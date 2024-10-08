package com.korea.WhereToGo.common;

public interface ResponseCode {
    String SUCCESS = "SU";
    String VALIDATION_FAIL = "VF";
    String DATABASE_ERROR = "DBE";
    String DUPLICATE_FESTIVAL = "DF";
    String NOT_EXISTED_FESTIVAL = "NF";
    String DO_NOT_HAVE_PERMISSION = "DHP";

    String NOT_EXISTED_QUESTION = "NQ";
    String NOT_EXISTED_ANSWER = "NA";
    String NOT_EXISTED_NOTICE = "NN";
    String NOT_EXISTED_REVIEW = "NR";

    String CERTIFICATION_FAIL = "CF";
    String DUPLICATE_ID = "DI";
    String DUPLICATE_EMAIL = "DE";
    String DUPLICATED_NICKNAME = "DN";
    String SIGN_IN_FAIL = "SF";
    String MAIL_FAIL = "MF";
    String NOT_EXISTED_USER = "NU";
    String WRONG_PASSWORD = "WP";
    String NOT_EXISTED_CHAT_ROOM = "NCR";
    String NOT_EXISTED_CHAT_MESSAGE = "NCM";
    String ALREADY_EXIST_CHAT_ROOM = "AECR";
    String NOT_EXISTED_MEETING = "NM";
    String NOT_EXISTED_FAVORITE = "NFA";
    String FALSE_AGREEMENT = "FA";
    String ALREADY_REQUESTED = "AR";
    String NOT_EXISTED_JOIN_REQUEST = "NEJR";
    String NOT_EXISTED_MEETING_BOARD = "NMB";
    String NOT_EXISTED_BOARD_REPLY = "NBR";
    String NOT_EXISTED_REPLY_REPLY = "NRR";
    String NOT_EXISTED_BOARD = "NB";

    String CANNOT_CREATE_MEETING = "CCM";
    String BLOCKED_USER = "BU";

}
