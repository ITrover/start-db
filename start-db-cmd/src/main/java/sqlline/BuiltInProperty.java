/*
 * Copyright 2022 ST-Lab
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License version 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */

package sqlline;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jline.reader.LineReader;
import org.jline.reader.impl.history.DefaultHistory;
import org.urbcomp.start.db.cmd.Main;

/**
 * Built-in properties of SqlLine.
 *
 * <p>Every property must implement the {@link SqlLineProperty} interface;
 * it is convenient to put properties in this {@code enum} but not mandatory.
 */
public enum BuiltInProperty implements SqlLineProperty {

    AUTO_COMMIT("autoCommit", Type.BOOLEAN, true),
    AUTO_PAIRING("autoPairing", Type.BOOLEAN, true),
    AUTO_RESIZE("autoResize", Type.BOOLEAN, false),
    AUTO_SAVE("autoSave", Type.BOOLEAN, false),
    COLOR_SCHEME(
        "colorScheme",
        Type.STRING,
        DEFAULT,
        true,
        false,
        new Application().getName2HighlightStyle().keySet()
    ),
    COLOR("color", Type.BOOLEAN, false),
    CONFIRM("confirm", Type.BOOLEAN, false),
    CONFIRM_PATTERN("confirmPattern", Type.STRING, "^(?i:(DROP|DELETE))"),
    CONNECT_INTERACTION_MODE(
        "connectInteractionMode",
        Type.STRING,
        new Application().getDefaultInteractiveMode(),
        true,
        false,
        new HashSet<>(new Application().getConnectInteractiveModes())
    ),
    CONNECTION_CONFIG("connectionConfig", Type.FILE_PATH, ""),
    CSV_DELIMITER("csvDelimiter", Type.STRING, ","),

    CSV_QUOTE_CHARACTER("csvQuoteCharacter", Type.CHAR, '\''),

    DATE_FORMAT("dateFormat", Type.STRING, DEFAULT),
    ESCAPE_OUTPUT("escapeOutput", Type.BOOLEAN, false),

    FAST_CONNECT("fastConnect", Type.BOOLEAN, true),
    FORCE("force", Type.BOOLEAN, false),
    HEADER_INTERVAL("headerInterval", Type.INTEGER, 100),
    HISTORY_FILE(
        "historyFile",
        Type.FILE_PATH,
        new File(SqlLineOpts.saveDir(), "history").getAbsolutePath()
    ),
    HISTORY_FLAGS("historyFlags", Type.STRING, "-d"),
    INCREMENTAL("incremental", Type.BOOLEAN, false),
    INCREMENTAL_BUFFER_ROWS("incrementalBufferRows", Type.INTEGER, 1000),
    ISOLATION(
        "isolation",
        Type.STRING,
        "TRANSACTION_REPEATABLE_READ",
        true,
        false,
        new HashSet<>(new Application().getIsolationLevels())
    ),
    KEEP_SEMICOLON("keepSemicolon", Type.BOOLEAN, false),
    LIVE_TEMPLATES("liveTemplates", Type.FILE_PATH, ""),
    MAX_COLUMN_WIDTH("maxColumnWidth", Type.INTEGER, -1),
    // don't save maxheight, maxwidth: it is automatically set based on
    // the terminal configuration
    MAX_HEIGHT("maxHeight", Type.INTEGER, 80, false, false, null),
    // start-db modify start
    MAX_WIDTH("maxWidth", Type.INTEGER, Main.getTableMaxWidth(), false, false, null),
    // start-db modify end

    MAX_HISTORY_ROWS("maxHistoryRows", Type.INTEGER, DefaultHistory.DEFAULT_HISTORY_SIZE),
    MAX_HISTORY_FILE_ROWS(
        "maxHistoryFileRows",
        Type.INTEGER,
        DefaultHistory.DEFAULT_HISTORY_FILE_SIZE
    ),

    MODE(
        "mode",
        Type.STRING,
        LineReader.EMACS,
        true,
        false,
        new HashSet<>(Arrays.asList(LineReader.EMACS, "vi"))
    ),
    NUMBER_FORMAT("numberFormat", Type.STRING, DEFAULT),
    NULL_VALUE("nullValue", Type.STRING, DEFAULT),
    SILENT("silent", Type.BOOLEAN, false),
    OUTPUT_FORMAT("outputFormat", Type.STRING, "table"),
    TABLE_STYLE(
        "tableStyle",
        Type.STRING,
        "default",
        true,
        false,
        new Application().getName2TableOutputFormatStyle().keySet()
    ),
    PROMPT("prompt", Type.STRING, "sqlline> "),
    PROMPT_SCRIPT("promptScript", Type.STRING, ""),
    PROPERTIES_FILE(
        "propertiesFile",
        Type.FILE_PATH,
        new File(SqlLineOpts.saveDir(), "sqlline.properties").getAbsolutePath()
    ),
    READ_ONLY("readOnly", Type.BOOLEAN, false),
    RIGHT_PROMPT("rightPrompt", Type.STRING, ""),
    ROW_LIMIT("rowLimit", Type.INTEGER, 0),
    SHOW_ELAPSED_TIME("showElapsedTime", Type.BOOLEAN, true),
    SCRIPT_ENGINE(
        "scriptEngine",
        Type.STRING,
        "nashorn",
        true,
        false,
        new Application().getAvailableScriptEngineNames()
    ),
    SHOW_COMPLETION_DESCR("showCompletionDesc", Type.BOOLEAN, true),
    SHOW_HEADER("showHeader", Type.BOOLEAN, true),
    SHOW_LINE_NUMBERS("showLineNumbers", Type.BOOLEAN, false),
    SHOW_NESTED_ERRS("showNestedErrs", Type.BOOLEAN, false),
    SHOW_TYPES("showTypes", Type.BOOLEAN, false),
    SHOW_WARNINGS("showWarnings", Type.BOOLEAN, true),
    STRICT_JDBC("strictJdbc", Type.BOOLEAN, false),
    TIME_FORMAT("timeFormat", Type.STRING, DEFAULT),
    TIMEOUT("timeout", Type.INTEGER, -1),
    TIMESTAMP_FORMAT("timestampFormat", Type.STRING, DEFAULT),
    TRIM_SCRIPTS("trimScripts", Type.BOOLEAN, true),
    USE_LINE_CONTINUATION("useLineContinuation", Type.BOOLEAN, true),
    VERBOSE("verbose", Type.BOOLEAN, false),
    VERSION("version", Type.STRING, new Application().getVersion(), false, true, null);

    private final String propertyName;
    private final Type type;
    private final Object defaultValue;
    private final boolean couldBeStored;
    private final boolean isReadOnly;
    private final Set<String> availableValues;

    BuiltInProperty(String propertyName, Type type, Object defaultValue) {
        this(propertyName, type, defaultValue, true, false, null);
    }

    BuiltInProperty(
        String propertyName,
        Type type,
        Object defaultValue,
        boolean couldBeStored,
        boolean isReadOnly,
        Set<String> availableValues
    ) {
        this.propertyName = propertyName;
        this.type = type;
        this.defaultValue = defaultValue;
        this.isReadOnly = isReadOnly;
        this.couldBeStored = couldBeStored;
        this.availableValues = availableValues == null
            ? Collections.emptySet()
            : Collections.unmodifiableSet(availableValues);
    }

    @Override
    public String propertyName() {
        return propertyName;
    }

    @Override
    public Object defaultValue() {
        return defaultValue;
    }

    @Override
    public boolean isReadOnly() {
        return isReadOnly;
    }

    @Override
    public boolean couldBeStored() {
        return couldBeStored;
    }

    @Override
    public Type type() {
        return type;
    }

    @Override
    public Set<String> getAvailableValues() {
        return availableValues;
    }

    /**
     * Returns the built-in property with the given name, or null if not found.
     *
     * @param propertyName Property name
     * @param ignoreCase   Whether to ignore case
     * @return Property, or null if not found
     */
    public static SqlLineProperty valueOf(String propertyName, boolean ignoreCase) {
        for (SqlLineProperty property : values()) {
            if (compare(propertyName, property.propertyName(), ignoreCase)) {
                return property;
            }
        }
        return null;
    }

    private static boolean compare(String s0, String s1, boolean ignoreCase) {
        return ignoreCase ? s1.equalsIgnoreCase(s0) : s1.equals(s0);
    }
}

// End BuiltInProperty.java
