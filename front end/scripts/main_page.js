document.querySelector('#submitButton').addEventListener('click', () => {
    localStorage.setItem("disabledParking", document.getElementsByName('form-disabledParking')[0].checked);
    localStorage.setItem("guardedParking", document.getElementsByName('form-guardedParking')[0].checked);
    localStorage.setItem("paidParking", document.getElementsByName('form-paidParking')[0].checked);
    localStorage.setItem("hugeParking", document.getElementsByName('form-hugeParking')[0].checked);
    localStorage.setItem("privateParking", document.getElementsByName('form-privateParking')[0].checked);
    localStorage.setItem("electricCarParking", document.getElementsByName('form-electricCarParking')[0].checked);
    window.location.href = 'second_page.html';
});