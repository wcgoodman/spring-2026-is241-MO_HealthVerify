function togglePassword(fieldId) {
    let field = document.getElementById(fieldId);
    field.type = field.type === "password" ? "text" : "password";
}

document.getElementById("signupForm").addEventListener("submit", function (e) {
    e.preventDefault();

    let email = document.getElementById("email");
    let emailError = document.getElementById("emailError");
    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("confirmPassword").value;

    let isValid = true;

    // Clear previous errors
    emailError.textContent = "";
    email.classList.remove("error");

    // Email regex pattern
    const emailRegexPattern = "^(?=.{1,254}$)(?=.{1,64}@)[A-Za-z0-9!#$%&'*+/=?^_{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/\\" +
        "=?^_{|}~-]+)*@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z]{2,}|\\[(?:IPv6:[A-F0-9]{0,4}(?::[A-F0-9]\{" +
        "0,4}){2,7}|(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\]$";

    if (!emailRegexPattern.test(email.value)) {
        emailError.textContent = "Please enter a valid email address.";
        email.classList.add("error");
        isValid = false;
    }

    if (password !== confirmPassword) {
        alert("Passwords do not match.");
        isValid = false;
    }

    if (isValid) {
        alert("Account successfully created!");
    }
});