package com.fmetin.readingisgood.shared;

public class RestResponseMessage {
    public final static String MSG_FORBIDDEN_ERROR = "{forbidden.error}";
    public final static String MSG_VALIDATION_ERROR = "{validation.error}";
    public final static String MSG_BAD_CREDENTIAL = "{bad.credential}";
    public final static String MSG_UNAUTHORIZED = "{unauthorized.error}";
    public final static String MSG_BOOK_NOT_FOUND = "{book.not.found}";
    public final static String MSG_THERE_IS_NOT_STOCK = "{there.is.not.stock}";
    public final static String MSG_UNKNOWN_ERROR = "{unknown.error}";
    public final static String MSG_ORDER_NOT_FOUND = "{order.not.found}";
    public final static String MSG_REDIS_LOCK_ERROR = "{redlock.error}";
    public final static String MSG_TRANSACTION_TIMEOUT = "{transaction.timeout}";
    public final static String MSG_VALIDATION_CONSTRAINT_UNIQUEBOOKNAME = "{validation.constraint.UniqueBookName.message}";
    public final static String MSG_VALIDATION_CONSTRAINT_UNIQUEEMAIL = "{validation.constraint.UniqueEmail.message}";
    public final static String MSG_VALIDATION_CONSTRAINT_NAME_NOTNULL = "{validation.constraint.name.NotNull.message}";
    public final static String MSG_VALIDATION_CONSTRAINT_NAME_SIZE = "{validation.constraint.name.Size.message}";
    public final static String MSG_VALIDATION_CONSTRAINT_SURNAME_NOTNULL = "{validation.constraint.surname.NotNull.message}";
    public final static String MSG_VALIDATION_CONSTRAINT_SURNAME_SIZE = "{validation.constraint.surname.Size.message}";
    public final static String MSG_VALIDATION_CONSTRAINT_PRICE_NOTNULL = "{validation.constraint.price.NotNull.message}";
    public final static String MSG_VALIDATION_CONSTRAINT_STOCK_NOTNULL = "{validation.constraint.stock.NotNull.message}";
    public final static String MSG_VALIDATION_CONSTRAINT_BOOK_ID_NOTNULL = "{validation.constraint.book.id.NotNull.message}";
    public final static String MSG_VALIDATION_CONSTRAINT_ORDER_LIST_NOTNULL = "{validation.constraint.order.list.NotNull.message}";
    public final static String MSG_VALIDATION_CONSTRAINT_ORDER_LIST_NOTEMPTY = "{validation.constraint.order.list.NotEmpty.message}";
    public final static String MSG_VALIDATION_CONSTRAINT_EMAIL_NOTNULL = "{validation.constraint.email.NotNull.message}";
    public final static String MSG_VALIDATION_CONSTRAINT_EMAIL_SIZE = "{validation.constraint.email.Size.message}";

    public final static String MSG_VALIDATION_CONSTRAINT_PASSWORD_NOTNULL = "{validation.constraint.password.NotNull.message}";
    public final static String MSG_VALIDATION_CONSTRAINT_PASSWORD_PATTERN = "{validation.constraint.password.Pattern.message}";
    public final static String MSG_VALIDATION_CONSTRAINT_EMAIL_PATTERN = "{validation.constraint.email.Pattern.message}";

}
