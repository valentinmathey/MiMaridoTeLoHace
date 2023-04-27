const userCards = document.querySelectorAll('.card');
const userInfoDiv = document.getElementById('user-info');
const userInfoProviderDiv = document.getElementById('user-info-provider');

userCards.forEach(card => {
  card.addEventListener('click', function () {
    const title = card.querySelector('.card-title').textContent;
    userInfoDiv.style.display = 'block';
    if (title === "Proveedor") {
      userInfoProviderDiv.style.display = 'block';
    } else {
      userInfoProviderDiv.style.display = 'none';
    }
  });
});