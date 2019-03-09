package work.erio.toolkit.engine;

import com.google.common.io.ByteStreams;
import work.erio.toolkit.Toolkit;

import javax.script.*;
import java.io.*;

public class EngineManager {
    private static EngineManager instance;
    private ScriptEngine scriptEngine;

    private EngineManager() {
        scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
    }

    public void babelEval(String code) throws ScriptException, IOException {
        ScriptContext context = new SimpleScriptContext();
        Bindings bindings = scriptEngine.createBindings();
        context.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        context.getBindings(ScriptContext.ENGINE_SCOPE).put("global", bindings);
        String timeoutCode = new String(ByteStreams.toByteArray(Toolkit.class.getClassLoader().getResourceAsStream("timeout-polyfill.js")));
        String shimCode = new String(ByteStreams.toByteArray(Toolkit.class.getClassLoader().getResourceAsStream("es6-shim.js")));
        String babelCode = new String(ByteStreams.toByteArray(Toolkit.class.getClassLoader().getResourceAsStream("babel-standalone.js")));
        scriptEngine.eval("global.console = Java.type('work.erio.toolkit.engine.api.Console');", bindings);
        scriptEngine.eval(timeoutCode, bindings);
        scriptEngine.eval(shimCode, bindings);
        scriptEngine.eval(babelCode, bindings);
        context.getBindings(ScriptContext.ENGINE_SCOPE).put("source", code);
        String transformed = (String) scriptEngine.eval("Babel.transform(source, { presets: ['es2015']}).code", bindings);
        scriptEngine.eval(transformed, bindings);
    }

    public static EngineManager getInstance() {
        if (instance == null) {
            instance = new EngineManager();
        }
        return instance;
    }
}
