package by.vanhooijdonk.destroyerdroid.rest;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Yahor_Fralou on 3/22/2017 12:13 PM.
 */

public interface RobotApiClient {
    String ROOT_PATH = "/action";

    @GET(ROOT_PATH + "/ALL_MOTORS_POWER_ON")
    Observable<Void> motorsAllOn();

    @GET(ROOT_PATH + "/ALL_MOTORS_POWER_OFF")
    Observable<Void> motorsAllOff();

    @GET(ROOT_PATH + "/MOVE_FORWARD")
    Observable<Void> moveForward();

    @GET(ROOT_PATH + "/MOVE_STOP")
    Observable<Void> stopMove();

    @GET(ROOT_PATH + "/MOVE_BACKWARD")
    Observable<Void> moveBackward();

    @GET(ROOT_PATH + "/MOVE_LEFT")
    Observable<Void> moveLeft();

    @GET(ROOT_PATH + "/MOVE_RIGHT")
    Observable<Void> moveRight();
}
