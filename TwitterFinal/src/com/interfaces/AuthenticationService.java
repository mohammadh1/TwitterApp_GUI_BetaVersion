package com.interfaces;

import java.time.LocalDate;

/**
 * authentication interfaces
 * method for login and signup
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 */
public interface AuthenticationService {
    Boolean login(String username, String password);
    Boolean signup(String username, String password, String bio, String firstName, String lastName, LocalDate birthDate, LocalDate registrationDate);
    Boolean signup(String username, String password, String firstName, String lastName, LocalDate birthDate);
}
