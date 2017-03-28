function checkValidation() {
	var letters = /^[0-9a-zA-Z]+$/;
	var pan = document.getElementById("example-text-input-pan");
	
	if (pan.value.match(letters)) {
		return true;
	} else {
		alert('Please input alphanumeric characters only');
		return false;
	}
	var name = document.getElementById("example-text-input-name");
	if (name.value.match(letters)) {
		return true;
	} else {
		alert('Please input alphanumeric characters only');
		return false;
	}
}
