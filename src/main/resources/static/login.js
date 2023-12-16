document.querySelector('.img__btn').addEventListener('click', function () {
  document.querySelector('.cont').classList.toggle('s--signup');
});

document.getElementById('registrationForm').addEventListener('submit', function (event) {
  var password = document.getElementById('password').value;
  var confirmPassword = document.getElementById('confirmPassword').value;
  var passwordMatchMessage = document.getElementById('passwordMatchMessage');

  if (password !== confirmPassword) {
    passwordMatchMessage.textContent = 'Passwords do not match!';
    // alert('Passwords do not match!');
    event.preventDefault(); // Prevent form submission
  } else {
    passwordMatchMessage.textContent = ''; // Clear previous message
  }
});