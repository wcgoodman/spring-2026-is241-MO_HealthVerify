function togglePassword(fieldId) {
    var field = document.getElementById(fieldId);
    field.type = field.type === "password" ? "text" : "password";
}

// Validation and submit handling
(function() {
    var form = document.getElementById('signupForm');
    var firstName = document.getElementById('firstName');
    var lastName = document.getElementById('lastName');
    var email = document.getElementById('email');
    var emailError = document.getElementById('emailError');
    var password = document.getElementById('password');
    var confirmPassword = document.getElementById('confirmPassword');
    var securityQuestion = document.getElementById('securityQuestion');
    var securityAnswer = document.getElementById('securityAnswer');
    var terms = document.getElementById('terms');
    var signupButton = document.getElementById('signupButton');

    var touched = {};

    // Email regex: allows + in local-part and requires a TLD
    var emailPattern = new RegExp("^[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");
    var pwPattern = /^.{8,64}$/;

    function setError(el, show) {
        if (!el) return;
        if (show) el.classList.add('error');
        else el.classList.remove('error');
    }

    function validateField(id) {
        var el = document.getElementById(id);
        var valid = true;

        switch(id) {
            case 'firstName':
            case 'lastName':
            case 'securityAnswer':
                valid = el && el.value && el.value.trim().length > 0;
                break;
            case 'email':
                valid = el && emailPattern.test(el.value.trim());
                if (touched.email) {
                    emailError.textContent = valid ? '' : '*Please enter a valid email address.';
                }
                break;
            case 'password':
                valid = el && pwPattern.test(el.value);
                break;
            case 'confirmPassword':
                valid = confirmPassword && (confirmPassword.value === password.value) && pwPattern.test(confirmPassword.value);
                break;
            case 'securityQuestion':
                valid = el && el.value && el.value !== '';
                break;
            case 'terms':
                valid = terms && terms.checked;
                break;
            default:
                valid = true;
        }

        // Only show visual error if field has been touched at least once
        var showVisual = !!touched[id];
        if (id === 'email') setError(el, showVisual && !valid);
        else if (id === 'terms') {
            // checkbox uses parent label highlighting
            var cb = document.getElementById('terms');
            if (cb) cb.classList.toggle('error', showVisual && !valid);
        } else {
            setError(el, showVisual && !valid);
        }

        return valid;
    }

    function validateAll() {
        var ids = ['firstName','lastName','email','password','confirmPassword','securityQuestion','securityAnswer','terms'];
        var allValid = true;
        ids.forEach(function(id){
            var ok = validateField(id);
            if (!ok) allValid = false;
        });
        return allValid;
    }

    function updateSubmitState() {
        var allValid = validateAll();
        signupButton.disabled = !allValid;
        return allValid;
    }

    // Attach focus handlers to mark touched
    ['firstName','lastName','email','password','confirmPassword','securityQuestion','securityAnswer'].forEach(function(id){
        var el = document.getElementById(id);
        if (!el) return;
        el.addEventListener('focus', function(){ touched[id] = true; validateField(id); updateSubmitState(); });
        el.addEventListener('input', function(){ validateField(id); updateSubmitState(); });
        el.addEventListener('blur', function(){ validateField(id); updateSubmitState(); });
    });

    // checkbox change
    if (terms) {
        terms.addEventListener('change', function(){ touched['terms'] = true; validateField('terms'); updateSubmitState(); });
    }

    // Prevent submission unless valid
    form.addEventListener('submit', function(e){
        e.preventDefault();
        // mark all fields as touched so errors show if any
        ['firstName','lastName','email','password','confirmPassword','securityQuestion','securityAnswer','terms'].forEach(function(id){ touched[id]=true; });
        if (!updateSubmitState()) {
            // focus first invalid
            var ids = ['firstName','lastName','email','password','confirmPassword','securityQuestion','securityAnswer','terms'];
            for (var i=0;i<ids.length;i++){
                if (!validateField(ids[i])){
                    var el = document.getElementById(ids[i]);
                    if (el && typeof el.focus === 'function') el.focus();
                    break;
                }
            }
            return;
        }

        // Build payload and send to backend
        var payload = {
            firstName: firstName.value.trim(),
            lastName: lastName.value.trim(),
            email: email.value.trim(),
            password: password.value,
            securityQuestion: securityQuestion.value,
            securityAnswer: securityAnswer.value.trim()
        };

        fetch('/api/register', {// makes an HTTP request to your backend endpoint
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        })
        .then(function(resp){ if (!resp.ok) throw new Error('Network response was not ok'); return resp.json(); }) // parse JSON response
        .then(function(data){ // handle response from backend
            if (data && data.success) {
                alert('Account registration successful!');
                window.location.href = 'index.html'; // Redirect to login page after successful registration
            } else { // show error message from backend if available
                var msg = (data && data.message) ? data.message : 'Registration failed.';
                alert(msg);
            }
        })
        .catch(function(err){ alert('Registration failed: ' + err.message); });
    });

})();

// Password tooltip: show only for the main password field (not confirm password)
(function() {
    var pwField = document.getElementById('password');
    var pwTooltip = document.getElementById('passwordTooltip');
    if (!pwField || !pwTooltip) return;

    pwField.addEventListener('focus', function() {
        pwTooltip.classList.add('visible');
        pwTooltip.setAttribute('aria-hidden', 'false');
    });

    pwField.addEventListener('blur', function() {
        pwTooltip.classList.remove('visible');
        pwTooltip.setAttribute('aria-hidden', 'true');
    });
})();