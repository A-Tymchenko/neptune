package main.java.com.ra.courses.airport.dao.impl;

public class PlaneDaoException extends Exception {

        public PlaneDaoException(final String message) {
            super(message);
        }

        public PlaneDaoException(final String message, final Throwable cause) {
            super(message, cause);
        }
}
