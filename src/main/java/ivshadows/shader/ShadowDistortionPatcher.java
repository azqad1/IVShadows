package ivshadows.shader;

import ivshadows.IVShadowsConfig;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ShadowDistortionPatcher {

    private static final Logger LOGGER = LogManager.getLogger("ivshadows");

    private static final Pattern SHADOW_DISTORTION = Pattern.compile(
        "(\\bdistort\\w*\\s*=\\s*)([^;=]*?)\\*\\s*([A-Za-z_][A-Za-z0-9_]*|[0-9]*\\.?[0-9]+)\\s*\\+\\s*\\(\\s*1\\.0\\s*-\\s*\\3\\s*\\)"
    );

    private static final Set<String> REPORTED = Collections.synchronizedSet(new HashSet<>());

    private static volatile boolean announced;

    private ShadowDistortionPatcher() {
    }

    public static String patch(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }

        double override = IVShadowsConfig.shadowDistortionOverride();

        if (override < 0.0D) {
            return source;
        }

        if (!announced) {
            announced = true;
            LOGGER.info("[ivshadows] shadow distortion override {} is enabled, scanning shader sources", override);
        }

        String value = glslFloat(override);
        Matcher matcher = SHADOW_DISTORTION.matcher(source);
        StringBuilder patched = null;

        while (matcher.find()) {
            if (patched == null) {
                patched = new StringBuilder(source.length());
            }

            String replacement;

            if (override == 0.0D) {
                replacement = matcher.group(1) + "1.0";
            } else {
                replacement = matcher.group(1) + matcher.group(2) + "* " + value + " + (1.0 - " + value + ")";
            }

            if (REPORTED.add(matcher.group())) {
                LOGGER.info("[ivshadows] shadow distortion [{}] rewritten to [{}]", matcher.group().trim(), replacement.trim());
            }

            matcher.appendReplacement(patched, Matcher.quoteReplacement(replacement));
        }

        if (patched == null) {
            return source;
        }

        matcher.appendTail(patched);
        return patched.toString();
    }

    private static String glslFloat(double value) {
        String text = Double.toString(value).replace('E', 'e');

        if (text.indexOf('.') < 0 && text.indexOf('e') < 0) {
            text = text + ".0";
        }

        return text;
    }
}
