import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * BookServlet
 */
@WebServlet({"/book", "/book/delete","/book/insert","/book/edit","/book/update"})
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private BookDAO bookDAO;

    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

        bookDAO = new BookDAO(jdbcURL, jdbcUsername, jdbcPassword);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String action = req.getServletPath();
        System.out.println(action);
        try {
            switch (action) {
            case "/book/insert":
                System.out.println("new action called");
                   insertBook(req, resp);
                   //listBook(req, resp);
                break;
            case "/book/delete":
                System.out.println("delete action called");
                  deleteBook(req, resp);
                  System.out.println("deleted");
                 
                  //System.out.println("displayed");
                break;
            case "/book/edit":
                System.out.println("update action called");
                   updateBookform(req, resp);
                break;
                case "/book/update":
                System.out.println("update action called");
                   updateBook(req, resp);
                break;
            default:
                System.out.println("list action called");
                listBook(req, resp);
                break;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       

    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        float price = Float.parseFloat(request.getParameter("price"));
 
        Book book = new Book(id, title, author, price);
        bookDAO.updateBook(book);
        response.sendRedirect("/");
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));

    Book book = new Book(id);
    bookDAO.deleteBook(book);
    response.sendRedirect("/");
    }

    private void insertBook(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
    String title = request.getParameter("title");
    String author = request.getParameter("author");
    float price = Float.parseFloat(request.getParameter("price"));

    Book newBook = new Book(title, author, price);
    bookDAO.insertBook(newBook);
    response.sendRedirect("/");
    }

    private void updateBookform(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, ServletException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));
    Book existingBook = bookDAO.getBook(id);
    RequestDispatcher dispatcher = request.getRequestDispatcher("/");
    request.setAttribute("book", existingBook);
    dispatcher.forward(request, response);
    // bookDAO.updateBook(existingBook);
    // response.sendRedirect("/");
}

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void listBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Book> listBook = bookDAO.listAllBooks();
        request.setAttribute("listBook", listBook);

        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(listBook, new TypeToken<List<Book>>() {}.getType());        
        JsonArray jsonArray = element.getAsJsonArray();
        response.setContentType("application/json");
        response.getWriter().print(jsonArray);

        // RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        // dispatcher.forward(request, response);
        
    }
}