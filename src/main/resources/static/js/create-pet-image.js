var input = document.getElementById('inputPicture');
var container = document.getElementById('imagePreview');

if(input.value) {
  container.src = input.value;
  container.style.visibility = "visible";
}

input.addEventListener('input', () => {
    container.src = input.value;
    container.style.visibility = "visible";
})

