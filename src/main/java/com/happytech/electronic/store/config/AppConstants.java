package com.happytech.electronic.store.config;

public class AppConstants {

    // urls
    public static final String USER_URL = "/api/users";
    public static final String CATEGORY_URL = "/api/categories";

    // pagination & sorting
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "5";
    public static final String NO_VALUE = "pageNumber";
    public static final String SIZE_VALUE = "pageSize";
    public static final String BY_VALUE = "sortBy";
    public static final String DIR_VALUE = "sortDir";
    public static final String SORT_BY = "userId";
    public static final String SORT_DIR = "asc";
    public static final String SORT_BYCAT = "categoryId";
    public static final String SORT_BYProd = "productId";
    public static final String SORT_DIR_DESC = "desc";

    //user
    public static final String USER = "user";
    public static final String USER_ID = "userId";
    public static final String USER_EMAIL = "User not found with given email id...!!";
    public static final String USER_DELETE = "User deleted successfully...!!";
    public static final String FILE_UPLOADED = "File uploaded successfully..!!";

    public static final String NAME_SIZE = "username must be min 4 characters..!!";
    public static final String EMAIL_REGEXP = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$";

    public static final String EMAIL_MSG =  "Email is required...!!";
    public static final String PASSWORD_MSG =  "Password is required..!!";
    public static final String GENDER_MSG =  "Invalid gender..!!";
    public static final String ABOUT_MSG =  "Write something about yourself..!!";


    public static final String USER_IMAGE_PATH = "${user.profile.image.path}";

    //category
    public static final String CATEGORY = "category";
    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_DELETE = "Category deleted successfully with : ";
    public static final String CATEGORY_FILE_UPLOADED = "File uploaded successfully..!!";
    public static final String TITLE_MESSAGE = "title must be required....!!!";
    public static final String TITLE_SIZE_MSG = "title must be of minimum 4 character...!!";
    public static final String DESCRIPTION_MSG = "Description required..!!";
    public static final String COVERIMAGE_MSG = "cover image required..!!";
    public static final String CATEGORY_IMAGE_PATH= "${category.profile.image.path}";

    // IsActive(safe delete
    public static final String YES = "Y";
    public static final String NO = "N";

    //exceptions

    public static final String BAD_REQUEST =  "Bad Request...!! ";
    public static final String STRING_FORMAT ="%s not found with %s : %s";

    //image
    public static final String PNG =  ".png";
    public static final String JPG =  ".jpg";
    public static final String JPEG =  ".jpeg";

    //product

    public static final String PRODUCT = "product";
    public static final String PRODUCT_ID = "productId";
    public static final String PRODUCT_DELETE = "Product deleted successfully...!!";



}
