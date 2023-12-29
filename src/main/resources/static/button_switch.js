function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
}

function checkAuthenticationStatus() {
  const accessToken = getCookie('accessToken');
  const refreshToken = getCookie('refreshToken');
  const isAuthenticated = accessToken && refreshToken;

  if (accessToken && refreshToken) {
    updateLoginButton(true);
    updateDashboardLink(true);
  } else {
    updateLoginButton(false);
  }
}

function updateLoginButton(isAuthenticated) {
  const loginButton = document.getElementById('loginButton');

  if (isAuthenticated) {
    loginButton.innerText = 'Logout';
    loginButton.href = '/logout';
  } else {
    loginButton.innerText = 'Login | Register';
    loginButton.href = '/register';
  }
}

function updateDashboardLink(isAuthenticated) {
  const dashboardLink = document.getElementById('dashboardLink');

  if (isAuthenticated) {
    dashboardLink.innerText = 'Dashboard';
    dashboardLink.href = '/authenticated';
  }
}

// Call the function to check the authentication status when the page loads
document.addEventListener('DOMContentLoaded', function () {
  checkAuthenticationStatus();

  // Add click event listener to the loginButton
  const loginButton = document.getElementById('loginButton');
  loginButton.addEventListener('click', function (event) {
    // Prevent the default behavior of the link
    event.preventDefault();

    // Add your logic for login or logout here
    const isAuthenticated = loginButton.innerText === 'Logout';
    if (isAuthenticated) {
      window.location.href = '/logout';
    } else {
      window.location.href = '/register';
    }
  });

  // Add click event listener to the dashboardLink
  const dashboardLink = document.getElementById('dashboardLink');
  dashboardLink.addEventListener('click', function (event) {
    // Prevent the default behavior of the link
    event.preventDefault();

    const isAuthenticated = dashboardLink.innerText === 'Dashboard';
    if (isAuthenticated) {
      window.location.href = '/authenticated';
    }
  });
});
