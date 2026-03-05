/*
    Spring 2026 IS 241.210 - Team MO HealthVerify
    Written by Davis Ly
 */

function togglePassword(fieldId) {
    let field = document.getElementById(fieldId);
    field.type = field.type === "password" ? "text" : "password";
}

(function () {
    let form = document.getElementById("loginForm");
    let username = document.getElementById("username");
    let password = document.getElementById("password");
    let usernameError = document.getElementById("usernameError");
    let passwordError = document.getElementById("passwordError");
    let signInButton = document.getElementById("signInButton");
    let touched = {};

    if (!form || !username || !password || !signInButton) return;

    // Email regex from registration script family: supports common valid local-part characters.
    let emailPattern = new RegExp("^[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");
    let pwPattern = /^.{8,64}$/;

    function setError(el, show) {
        if (!el) return;
        if (show) el.classList.add("error");
        else el.classList.remove("error");
    }

    function validateField(id) {
        let el = document.getElementById(id);
        let valid = true;

        if (id === "username") {
            valid = el && emailPattern.test(el.value.trim());
            if (usernameError) {
                usernameError.textContent = touched.username && !valid ? "Enter a valid email address." : "";
            }
        } else if (id === "password") {
            valid = el && pwPattern.test(el.value);
            if (passwordError) {
                passwordError.textContent = touched.password && !valid ? "Password must be 8-64 characters." : "";
            }
        }

        setError(el, !!touched[id] && !valid);
        return valid;
    }

    function validateAll() {
        let usernameValid = validateField("username");
        let passwordValid = validateField("password");
        return usernameValid && passwordValid;
    }

    function updateSubmitState() {
        signInButton.disabled = !validateAll();
    }

    ["username", "password"].forEach(function (id) {
        let el = document.getElementById(id);
        if (!el) return;

        el.addEventListener("focus", function () {
            touched[id] = true;
            validateField(id);
            updateSubmitState();
        });

        el.addEventListener("input", function () {
            validateField(id);
            updateSubmitState();
        });

        el.addEventListener("blur", function () {
            validateField(id);
            updateSubmitState();
        });
    });

    // No login request yet; only block invalid form submission.
    form.addEventListener("submit", function (e) {
        e.preventDefault();
        touched.username = true;
        touched.password = true;
        updateSubmitState();
    });

    updateSubmitState();
})();
