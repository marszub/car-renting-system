export const coordinatesValidators = {

    validateLatitude(latitude) {
        var value = parseFloat(latitude);
        if(isNaN(value) || value < -90 || value > 90) {
            return "Inserted value must be a float in range [-90, 90]";
        }
        return "";
    },

    validateLongitude(longitude) {
        var value = parseFloat(longitude);
        if(isNaN(value) || value < -180 || value > 180) {
            return "Inserted value must be a float in range [-180, 180]";
        }
        return "";
    }
}