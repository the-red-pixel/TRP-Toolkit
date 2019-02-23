package work.erio.toolkit.util;

import com.google.common.io.ByteStreams;
import work.erio.toolkit.Toolkit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.io.IOException;
import java.io.InputStream;

public class JSEngine {

    public ScriptEngine engine;
    private SimpleBindings bindings;

    public JSEngine() {
        this.engine = new ScriptEngineManager().getEngineByName("nashorn");;
    }

    public void eval(String code) {
        try {
            bindings = new SimpleBindings();
            engine.eval(loadLibCode("babel.min.js"), bindings);
            engine.eval(loadLibCode("evalPlugin.js"), bindings);
            bindings.put("input", code);
            engine.eval("Babel.transform(input, { plugins:['babel-eval'], presets: [['es2015',{modules:false}]]}).code", bindings);
        } catch (ScriptException | IOException e) {
            e.printStackTrace();
        }
    }

    public String loadLibCode(String name) throws IOException {
        InputStream inputStream = Toolkit.class.getClassLoader().getResourceAsStream(name);
        return new String(ByteStreams.toByteArray(inputStream));
    }
}
