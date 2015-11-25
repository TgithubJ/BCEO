*Coding Progress*

0. login form (LoginActivity)
    --> checks the validation of the form fields
    --> required fields won't let the user pass
    --> email should have at least one "@" and "."
    --> password has to be at least length 4
    --> prompts dialog to stop user going to next page

1. Camera upload (SellActivity.java)
    --> takes picture, set the new photo right next to the toaster photo

2. Database CRUD (for local server) + DatabaseConnector
    1. Create - create User or Product entities.
        1) createUser
        2) createProduct

    2. Read - read User or Product entities
        1) getOneUser
        2) getAllUser
        3) getOneProduct
        4) getAllProduct

    3. Update - update User or Product entities
        1) updateUser
        2) updateProduct
    4. Delete - delete User or Product entities
        1) deleteUser
        2) deleteProduct

    5. Databaseconnector -
        create 4 tables (User, Group, Product, and UserProduct),
        open and close connection with the local database.
        1) open
        2) close

* Snapshots are in screenshot.pdf file *