package work.erio.toolkit.engine;

import com.google.common.io.ByteStreams;
import work.erio.toolkit.Toolkit;

import javax.naming.Binding;
import javax.script.*;
import java.io.*;

public class EngineManager {
    private static EngineManager instance;
    private ScriptEngine scriptEngine;

//    private Bindings engineBindings;

    private EngineManager() {
        scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
//        this.engineBindings = scriptEngine.getContext().getBindings(ScriptContext.ENGINE_SCOPE);
//        initializeEngineBindings();
    }

//    public Bindings getInitializedBindings() {
//        try {
//            Bindings bindings = scriptEngine.getContext().getBindings(ScriptContext.ENGINE_SCOPE);
//            scriptEngine.put("global", bindings);
//            String shimCode = new String(ByteStreams.toByteArray(Toolkit.class.getClassLoader().getResourceAsStream("es6-shim.js")));
//            scriptEngine.eval("global.console = Java.type('work.erio.toolkit.engine.api.Console');");
//            scriptEngine.eval(shimCode, bindings);
//            return bindings;
//        } catch (IOException | ScriptException e) {
//            e.printStackTrace();
//        }
//        return new SimpleBindings();
//    }

//    public void shimAndEval(String code) {
//        try {
//            Bindings bindings = getInitializedBindings();
//            scriptEngine.eval(code, bindings);
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        }
//    }

//    private void initializeEngineBindings() {
//        try {
//            scriptEngine.put("global", engineBindings);
//            String shimCode = new String(ByteStreams.toByteArray(Toolkit.class.getClassLoader().getResourceAsStream("es6-shim.js")));
//            String babelCode = new String(ByteStreams.toByteArray(Toolkit.class.getClassLoader().getResourceAsStream("index.js")));
//            scriptEngine.eval("global.console = Java.type('work.erio.toolkit.engine.api.Console');", engineBindings);
//            scriptEngine.eval(shimCode, engineBindings);
//            scriptEngine.eval(babelCode, engineBindings);
//        } catch (IOException | ScriptException e) {
//            e.printStackTrace();
//        }
//    }

//    public Bindings getEngineBindings() {
//        return engineBindings;
//    }

//    public Bindings getNewBindings() {
//        try {
//            scriptEngine.put("global", engineBindings);
//            String shimCode = new String(ByteStreams.toByteArray(Toolkit.class.getClassLoader().getResourceAsStream("es6-shim.js")));
//            String babelCode = new String(ByteStreams.toByteArray(Toolkit.class.getClassLoader().getResourceAsStream("index.js")));
////            scriptEngine.eval("global.console = Java.type('work.erio.toolkit.engine.api.Console');", bindings);
//            scriptEngine.eval(shimCode, engineBindings);
//            scriptEngine.eval(babelCode, engineBindings);
//            return engineBindings;
//        } catch (IOException | ScriptException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public void babelEval(String code) throws ScriptException, IOException {
        ScriptContext context = new SimpleScriptContext();
        Bindings bindings = scriptEngine.createBindings();
        context.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        context.getBindings(ScriptContext.ENGINE_SCOPE).put("global", bindings);
        String shimCode = new String(ByteStreams.toByteArray(Toolkit.class.getClassLoader().getResourceAsStream("es6-shim.js")));
        String babelCode = new String(ByteStreams.toByteArray(Toolkit.class.getClassLoader().getResourceAsStream("index.js")));
        String promiseCode = new String(ByteStreams.toByteArray(Toolkit.class.getClassLoader().getResourceAsStream("polyfill.min.js")));
        scriptEngine.eval("global.console = Java.type('work.erio.toolkit.engine.api.Console');", bindings);
        scriptEngine.eval(shimCode, bindings);
        scriptEngine.eval(babelCode, bindings);
        scriptEngine.eval(promiseCode, bindings);
        context.getBindings(ScriptContext.ENGINE_SCOPE).put("source", code);
        String transformed = (String) scriptEngine.eval("Babel.transform(source, { presets: ['es2015']}).code", bindings);
        scriptEngine.eval(transformed, bindings);
    }

    public ScriptEngine getScriptEngine() {
        return scriptEngine;
    }

    public static EngineManager getInstance() {
        if (instance == null) {
            instance = new EngineManager();
        }
        return instance;
    }
}
