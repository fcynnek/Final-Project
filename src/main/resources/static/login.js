// This function is for the toggle-slider for the login page
document.querySelector('.img__btn').addEventListener('click', function () {
  document.querySelector('.cont').classList.toggle('s--signup');
});

// This function is to check whether the passwords entered in the registration form are the same
document.getElementById('registrationForm').addEventListener('submit', async function (event) {
  var registrationPassword = document.getElementById('registrationPassword').value;
  var confirmPassword = document.getElementById('confirmPassword').value;
  var passwordMatchMessage = document.getElementById('passwordMatchMessage');

  console.log('registrationPassword:', password);
  console.log('Confirm Password:', confirmPassword);

  if (registrationPassword !== confirmPassword) {
    passwordMatchMessage.textContent = 'Passwords do not match!';
    // alert('Passwords do not match!');
    event.preventDefault(); // Prevent form submission
  } else {
    passwordMatchMessage.textContent = ''; // Clear previous message
  }
});

