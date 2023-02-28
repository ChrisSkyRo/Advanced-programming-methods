package domain.validators;

import domain.User;

public class UserValidator implements Validator<User> {
    /**
     * Validates a user using a ValidatorStrategy
     * @param user the user to validate
     * @param strategy the strategy to use for validation
     * @throws ValidatorException if user is invalid
     */
    public void validate(User user, ValidatorStrategy strategy) throws ValidatorException {
        if(strategy == ValidatorStrategy.Quick) {
            if(!quickValidation(user))
                throw new ValidatorException("Invalid User");
        }
        else if(strategy == ValidatorStrategy.Extensive) {
            String error = validateName(user) + validateEmail(user) + validatePassword(user);
            if(!error.isEmpty())
                throw new ValidatorException(error);
        }
    }

    /**
     * Performs a quick and unreliable validation
     * @param user the user to validate
     * @return - true if the user is valid
     *         - false otherwise
     */
    private boolean quickValidation(User user) {
        return !user.getFirstName().isEmpty() && !user.getLastName().isEmpty() && !user.getPassword().isEmpty() && !user.getEmail().isEmpty();
    }

    /**
     * Validates the first and last name of a user
     * @param user the user whose name to validate
     * @return a string containing error messages if there are any or an empty string otherwise
     */
    private String validateName(User user) {
        String fn = user.getFirstName();
        String ln = user.getLastName();
        String error = "";
        // names must not be empty
        if(fn.isEmpty())
            error += "first name is empty; ";
        else for(int i = 0; i < fn.length(); i++)
            if (!Character.isLetter(fn.charAt(i))) {
                error += "first name contains non-letter characters; ";
                break;
            }
        if(ln.isEmpty())
            error += "last name is empty; ";
        else for(int i = 0; i < ln.length(); i++)
            if (!Character.isLetter(ln.charAt(i))) {
                error += "last name contains non-letter characters; ";
                break;
            }
        return error;
    }

    /**
     * Validates the email of a user
     * @param user the user whose email to validate
     * @return  a string containing error messages if there are any or an empty string otherwise
     */
    private String validateEmail(User user) {
        String email = user.getEmail();
        String error = "";
        if(email.isEmpty())
            error += "email is empty; ";
        else if(!email.contains("@") || !email.contains("."))
            error += "email has an invalid format; ";
        return error;
    }

    /**
     * Validates the password of a user
     * @param user the user whose password to validate
     * @return  a string containing error messages if there are any or an empty string otherwise
     */
    private String validatePassword(User user) {
        String password = user.getPassword();
        String error = "";
        if(password.length() < 8)
            error = "password is too short; ";
        return error;
    }
}
