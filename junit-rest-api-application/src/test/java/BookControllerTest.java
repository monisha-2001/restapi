import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rest.app.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    Book RECORD_1 = new Book(1L,"Atomic Habits","How to build better habits",5);
    Book RECORD_2 = new Book(2L,"Thinking Fast And Slow","How to create good mental models about thinking",4);
    Book RECORD_3 = new Book(3L,"Grokking Algorithms","Learn algorithm in fun way",5);

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice(new RestExceptionHandler()).build();
    }

    @Test
    public void getAllRecords_success() throws Exception{
        List<Book> records = new ArrayList<>(Arrays.asList(RECORD_1,RECORD_2,RECORD_3));

        Mockito.when(bookRepository.findAll()).thenReturn(records);

        //matcher that request the controller and it response back
        mockMvc.perform(MockMvcRequestBuilders
                .get("/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())    //200 message is handle by import static org.hamcrest.Matchers.*; we no need to implement
                .andExpect(jsonPath("$",hasSize(3))) //checks whether it contains 3 objects
                .andExpect(jsonPath("$[2].name",is("Grokking Algorithms"))); //checks the content of 3rd object name is same or not

    }

    @Test
    public void getBookById_success() throws Exception{

        Mockito.when(bookRepository.findById(RECORD_1.getBookId())).thenReturn(java.util.Optional.of(RECORD_1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())    //200 messahe is handle by import static org.hamcrest.Matchers.*; we no need to implement
                .andExpect(jsonPath("$",notNullValue())) //checks whether it contains 3 objects
                .andExpect(jsonPath("$.name",is("Atomic Habits"))); //checks the content of 3rd object name is same or not

    }

    @Test
    public void getBookById_Notfound() throws Exception{
        Mockito.when(bookRepository.findById(4L)).thenThrow(new IdNotFoundException("book id is not present"));


        mockMvc.perform(MockMvcRequestBuilders
                .get("/book/4"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void createBookRecord_success() throws Exception{

        Book record = Book.builder()
                .BookId(4L)
                .name("Intrduction to C")
                .summary("The name but longer")
                .rating(5)
                .build();

        Mockito.when(bookRepository.save(record)).thenReturn(record);

        String content = objectWriter.writeValueAsString(record); //means convert to string

        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON) //content it will read from wt we have newly created
                .accept(MediaType.APPLICATION_JSON) //we accept in json format
                .content(content); //sent to client in string format

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.name",is("Intrduction to C")));

    }

    @Test
    public void updateBookRecord_success() throws Exception{
        Book updaterecord = Book.builder()
                .BookId(1L)
                .name("Upadated Book Name")
                .summary("Updated summary")
                .rating(1).build();

        Mockito.when(bookRepository.findById(RECORD_1.getBookId())).thenReturn(Optional.of(RECORD_1));

        Mockito.when(bookRepository.save(updaterecord)).thenReturn((updaterecord));

        String updateContent = objectWriter.writeValueAsString(updaterecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.name",is("Upadated Book Name")));

    }

    @Test
    public void deleteBookById_success() throws Exception{

        Mockito.when(bookRepository.findById(RECORD_2.getBookId())).thenReturn(Optional.of(RECORD_2));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/book/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void deleteBookById_Notfound() throws Exception {
        Mockito.when(bookRepository.findById(4L)).thenThrow(new IdNotFoundException("Please provide a valid ID"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/book/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

}
