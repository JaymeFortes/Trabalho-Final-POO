package dados;

public class GeoCalculator {

        private GeoCalculator() {}

        public static double calculaDistancia(double latitude1, double longitude1, double latitude2, double longitude2) {
            final int R = 6371;

            double distanciaLatitude = Math.toRadians(latitude2 - latitude1);
            double distanciaLongitude = Math.toRadians(longitude2 - longitude1);

            double a = Math.sin(distanciaLatitude / 2) * Math.sin(distanciaLatitude / 2) +
                    Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) *
                            Math.sin(distanciaLongitude / 2) * Math.sin(distanciaLongitude / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            return R * c;
        }

        public static double calculaArea(double raio) {
            return Math.PI * raio * raio;
        }
    }

