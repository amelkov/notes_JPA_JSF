package by.amelkov.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("by.amelkov.validator.LoginValidator")
public class LoginValidator implements Validator {

    private static final String LOGIN_PATTERN = "[A-Za-z0-9]+";

    private Pattern pattern;
    private Matcher matcher;

    public LoginValidator() {
        pattern = Pattern.compile(LOGIN_PATTERN);
    }

    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {

        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            String flag = uiComponent.getId();
            FacesMessage msg = null;
            if (flag.equals("username")) {
                msg = new FacesMessage("Login field must be not empty and use A-Z, a-z, 0-9.",
                                "Login field must be not empty and use A-Z, a-z, 0-9.");
            }
            if (flag.equals("password")) {
                msg = new FacesMessage("Password field must be not empty and use A-Z, a-z, 0-9.",
                                "Password field must be not empty and use A-Z, a-z, 0-9.");
            }

            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
