package com.twitter.server;

/**
 * Error codes define here as an enum
 *
 * Error code 1 : signup error
 * Error code 2 : login error
 * Error code 3 : wrong username
 * Error code 4 : information not found
 * Error code 5
 * Error code 6
 * Error code 7
 * Error code 8
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 */
public enum ErrorCode {
    ERROR_CODE_1(1),ERROR_CODE_2(2),ERROR_CODE_3(3),ERROR_CODE_4(4),ERROR_CODE_5(5),ERROR_CODE_6(6),ERROR_CODE_7(7);

    private int value;
    ErrorCode(int value) {
        this.value = value;
    }
}
