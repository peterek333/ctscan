package pl.inz.ctscan.model.validator;

import pl.inz.ctscan.model.ApplicationUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*" +
            "(.[A-Za-z]{2,})$";

    private int passwordMin = 4;
    private int passwordMax = 20;
    private int emailMin = 4;
    private int emailMax = 60;

    private boolean validateEmail(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validate(ApplicationUser user) throws Exception {
        String email = user.getEmail();
        if(email == null) {
            throw new Exception("Email can't be null");
        }
        if(!isInSize(email, emailMin, emailMax)) {
            throw new Exception("Email length must be from " + emailMin + " to " + emailMax);
        }
        if(!validateEmail(email)) {
            throw new Exception("Invalid email");
        }

        String password = user.getPassword();
        if(password == null) {
            throw new Exception("Password can't be null");
        }
        if(!isInSize(password, passwordMin, passwordMax)) {
            throw new Exception("Password length must be from " + passwordMin + " to " + passwordMax);
        }

        return true;
    }

    private boolean isInSize(String text, int min, int max) {
        return text.length() >= min && text.length() <= max;
    }
}
