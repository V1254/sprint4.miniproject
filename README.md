# CO2006 18-19 - SPRINT 4 - MINIPROJECT


This assignment is worth **20%** of the overall module mark and the mark is provided out of **100**:

## Marks
* **5**/5 for doing exercises in the lab sessions
* **30**/30 for the checkpoint submission
* **65**/65 for the release submission
* Total: **100**

### Codebase

The codebase consists of five packages:
* **springData** contains the main application and configuration classes.
* **springData.controller** contains the controllers
* **springData.domain** contains the domain classes.
* **springData.repository** contains the CRUD data repositories.
* **springData.services** contains a user details service.

The codebase consists of the following folders for JSPs:
* **WEB-INF/views** contains JSPs for creating and showing Todos.
* **WEB-INF/views/admin** contains JSPs for creating `OrganizerUser` instances.
* **WEB-INF/views/security** contains JSPs for logging in and out.
  
## Tasks:

1. Create a class `SecurityConfig` in package `springData`but copy only the part relevant for an encrypted HTTPS connection. **[3 marks]**
   * The other parts will be added later in different configurations. 
   
2. Create a **new digital certificate** using `keytool` with your **University username** as the full name in the certificate. Place the certificate in file `src/main/resources/keystore.jks`.**[2 marks]** 

3. Configure the web application in file `src/main/resources/application.properties` in order to use it via a secure connection, using the protocol `https`. **[2 mark]** 

4. In method `run(...)` of class `OrganizerApp`: Create the roles `ADMIN` with `id=0`, `MANAGER` with `id=1`, and `ASSISTANT` with `id=2` and store them in the database (you might need to inject a role repository). **[4 marks]**

5. In method `run(...)` of class `OrganizerApp`: Create an unmanaged user (class `OrganizerUser`) with login "admin", password "admin", and role `ADMIN` and store the user in the database (you might need to inject a user repository). **[2 marks]**
   * Make sure to encrypt the password using `BCryptPasswordEncoder`!
   
6. In method `configure(HttpSecurity http)` of class `SecurityConfig`: Configure the login and logout request mappings. **[2 marks]** 
 
7. In method `configure(HttpSecurity http)` of class `SecurityConfig`: Define the following access matrix and also add a final exception handling to the access denied page. **[4 marks]** 

| request | access |
|--|--|
| /admin/** | ADMIN |
| /create/** | MANAGER |
| /delete/** | MANAGER |
| /list/** | ASSISTANT, MANAGER |

8. In method `successLogin(Principal principal)` of controller `AuthenticationController` implement the following redirects on successful login **[2 marks]**:

   * Users with role `ADMIN` are redirected to `/admin/create` and users with all other roles are redirected to `/list`. The user object is already provided for you.

9. * Implement a validator for class `OrganizerUser` called `OrganizerUserValidator` in package `springData.controller` to reject users with an empty login name and to reject users with usernames that already exist int the database. For the second check in the validator you will need access to the database via a `UserRepository` instance.**[10 marks]**

10. * In method `addNewUser(...)` of class `AdminController` check the validation result (don't forget to apply the validator to parameter `OrganizerUser u`). In case of errors, show the view `admin/CreateUser`. Otherwise add the role with name `roleName` (method parameter) to the user `u`, save the user in the database, and show view `admin/done`. **[10 marks]**
  * You will need to look up the role in the database.
  * Don't forget to encode the password of the user.

11. Comment out the field `public static Organizer organizer = new Organizer();` in class `OrganizerApp` in package `springData`.
   * This will lead to a number of compilation errors in controllers.

12. In method `public String listTodos(Model model)` of class `DisplayTodoController` compute the list of todos **from the database** based on the role of a user as indicated below and show the view `NoTodo` if the list of todos is empty. Add the todos to model attribute `todos` and show the view `ListTodos` otherwise. **[6 marks]**
   * For a logged in user with role `MANAGER` collect all todos in all organizers of that manager.
   * For a logged in user with role `ASSISTANT` collect all todos from the database (assistants see the todos of all managers).
   
13. In method `public String next(Model model)` of class `DisplayTodoController` compute any todos **from the database** with the highest priority based on the role of a user as indicated below and show the view `NoTodo` if the list of todos is empty. Otherwise, store the todo as model attribute `todo` and show the view `NextTodo`. **[6 marks]**
   * For a logged in user with role `MANAGER` collect all todos in all organizers of that manager.
   * For a logged in user with role `ASSISTANT` collect all todos from the database (assistants see the todos of all managers).

14. In method `public String addNewTodo(@Valid @ModelAttribute("todo") Todo t, BindingResult result, Model model)` of class `OrganizerController`, replace line `OrganizerApp.organizer.addTodo(t);` by logic that **uses the database** to store the addition of the todo **[6 marks]**:
   * if the current user (see advice above) does not have an organizer, create one
   * add the todo `t` to the first organizer of the current user
   * save the user in the database to indirectly save the organizer and the todo
   
15. In method `public String deleteTodo(@RequestParam("id") int id)` of class `OrganizerController`, replace line `OrganizerApp.organizer.deleteTodo(id);` by logic that **uses the database** for the deletion of the todo **[6 marks]**:
   * this task has a similar challenge related to foreign keys as the one above
