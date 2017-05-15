package io.github.zwieback.relef.services;

import org.apache.commons.cli.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CommandLineService {

    public static final String OPTION_HELP = "h";
    public static final String OPTION_PARSER_FULL = "pf";
    public static final String OPTION_PARSER_CATALOG = "pc";
    public static final String OPTION_PARSER_PRODUCT = "pp";
    public static final String OPTION_EXPORT_BRAND = "eb";
    public static final String OPTION_EXPORT_CATALOG = "ec";
    public static final String OPTION_EXPORT_PRODUCT = "ep";
    public static final String OPTION_EXPORT_MANUFACTURER = "em";
    public static final String OPTION_EXPORT_TRADE_MARK = "etm";

    private static final List<String> OPTIONS_PARSER = Arrays.asList(OPTION_PARSER_FULL, OPTION_PARSER_CATALOG,
            OPTION_PARSER_PRODUCT);
    private static final List<String> OPTIONS_EXPORT = Arrays.asList(OPTION_EXPORT_BRAND, OPTION_EXPORT_CATALOG,
            OPTION_EXPORT_PRODUCT, OPTION_EXPORT_MANUFACTURER, OPTION_EXPORT_TRADE_MARK);

    public Options createOptions() {
        Options options = new Options();
        options.addOptionGroup(buildParserOptionGroup());
        options.addOptionGroup(buildExportOptionGroup());
        options.addOption(OPTION_HELP, "help", false, "print this message");
        return options;
    }

    private OptionGroup buildParserOptionGroup() {
        OptionGroup parserGroup = new OptionGroup();
        parserGroup.addOption(Option.builder(OPTION_PARSER_FULL)
                .longOpt("parser-full")
                .desc("parse all products on site")
                .hasArg()
                .argName("0 - fast parsing (no description and not all properties, default)\n" +
                        "1 - slow parsing (every field of product)")
                .build());
        parserGroup.addOption(Option.builder(OPTION_PARSER_CATALOG)
                .longOpt("parser-catalog")
                .desc("parse only this catalog ids")
                .hasArg()
                .argName("id1,id2,...,idN")
                .build());
        parserGroup.addOption(Option.builder(OPTION_PARSER_PRODUCT)
                .longOpt("parser-product")
                .desc("parse only this product ids")
                .hasArg()
                .argName("id1,id2,...,idN")
                .build());
        return parserGroup;
    }

    private OptionGroup buildExportOptionGroup() {
        OptionGroup parserGroup = new OptionGroup();
        parserGroup.addOption(Option.builder(OPTION_EXPORT_BRAND)
                .longOpt("export-brand")
                .desc("export all brands")
                .build());
        parserGroup.addOption(Option.builder(OPTION_EXPORT_CATALOG)
                .longOpt("export-catalog")
                .desc("export all catalogs")
                .build());
        parserGroup.addOption(Option.builder(OPTION_EXPORT_PRODUCT)
                .longOpt("export-product")
                .desc("export all products")
                .build());
        parserGroup.addOption(Option.builder(OPTION_EXPORT_MANUFACTURER)
                .longOpt("export-manufacturer")
                .desc("export all manufacturers")
                .build());
        parserGroup.addOption(Option.builder(OPTION_EXPORT_TRADE_MARK)
                .longOpt("export-trade-mark")
                .desc("export all trade marks")
                .build());
        return parserGroup;
    }

    public CommandLine createCommandLine(Options options, String[] args) throws ParseException {
        CommandLineParser cmdParser = new DefaultParser();
        return cmdParser.parse(options, args);
    }

    public void printHelp(Options options) {
        String footer = "\nPlease report issues at https://github.com/zwieback/relef-parser/issues";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar relef-parser-{version}.jar [command] <command options>", null, options, footer);
    }

    public boolean hasCommandLineParserOption(CommandLine cmd) {
        return OPTIONS_PARSER.stream().anyMatch(cmd::hasOption);
    }

    public boolean hasCommandLineExportOption(CommandLine cmd) {
        return OPTIONS_EXPORT.stream().anyMatch(cmd::hasOption);
    }
}
