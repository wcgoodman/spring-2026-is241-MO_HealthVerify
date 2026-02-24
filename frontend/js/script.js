function togglePassword(fieldId) {
    var field = document.getElementById(fieldId);
    field.type = field.type === "password" ? "text" : "password";
}

document.getElementById("signupForm").addEventListener("submit", function(e) {
    e.preventDefault();

    var email = document.getElementById("email");
    var emailError = document.getElementById("emailError");
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;

    let isValid = true;

    // Clear previous errors
    emailError.textContent = "";
    email.classList.remove("error");

    // Email regex pattern
    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailPattern.test(email.value)) {
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