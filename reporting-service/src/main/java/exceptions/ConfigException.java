package exceptions;

public class ConfigException extends Exception{

        public ConfigException () {
            super();
        }

        public ConfigException (String errorMessage) {
            super(errorMessage);
        }
}
