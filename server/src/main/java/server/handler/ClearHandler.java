package server.handler;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import io.javalin.http.Context;
import service.ClearService;

import java.util.Map;

public class ClearHandler {
    private final ClearService clearService;

    public ClearHandler(ClearService clearService){
        this.clearService = clearService;
    }
    public void clearApp(Context ctx) throws DataAccessException {

        clearService.clear();
        ctx.contentType("application/json");
        ctx.status(200);
        ctx.result(new Gson().toJson(Map.of()));
    }

}