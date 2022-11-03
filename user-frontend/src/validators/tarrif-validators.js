export const tarrifValidators = {
    validateTarrif(tarrif) {
        console.log(tarrif);
        var value = parseInt(tarrif*100);
        console.log(value);
        if(isNaN(value) || value <= 0) {
            return "The tarrif value should be a positive number"
        }
        return "";
    }
}
