export const authValidators = {
    validateLogin(login) {
        if(!loginRegex.test(login)) {
            return "Login's length must be between 4 and 50 characters. It cannot contain spaces";
        }
        return "";
    },
    
    validateEmail(email) {
        if(!emailRegex.test(email)) {
            return "Invalid email";
        }
        return "";
    },

    validatePassword(password) {
        if(password.length < 8 || password.length > 200) {
            return "Password should have at least 8 characters and less than 200 characters";
        }
        return "";
    },

    validatePasswordMatch(password, repeatPassword) {
        if(password !== repeatPassword) {
            return "Passwords didn’t match. Try again."
        }
        return "";
    }
}

const loginRegex = /^\S{4,40}/
const emailRegex = /^\S+@\S+(\.\S+)+$/
