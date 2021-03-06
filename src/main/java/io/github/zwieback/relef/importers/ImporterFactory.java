package io.github.zwieback.relef.importers;

import io.github.zwieback.relef.importers.excel.MsProductImporter;
import io.github.zwieback.relef.importers.excel.SamsonProductImporter;
import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static io.github.zwieback.relef.services.CommandLineService.OPTION_IMPORT_MY_SKLAD_PRODUCT;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_IMPORT_SAMSON_PRODUCT;

@Service
public class ImporterFactory {

    private final Function<String, MsProductImporter> msProductImporterFactory;
    private final Function<String, SamsonProductImporter> samsonProductImporterFactory;

    @Autowired
    public ImporterFactory(Function<String, MsProductImporter> msProductImporterFactory,
                           Function<String, SamsonProductImporter> samsonProductImporterFactory) {
        this.msProductImporterFactory = msProductImporterFactory;
        this.samsonProductImporterFactory = samsonProductImporterFactory;
    }

    /**
     * Determine and collect importers by options specified in the command line.
     *
     * @param cmd command line
     * @return list of importers specified in the command line
     * @throws IllegalArgumentException if no importer found
     */
    public List<Importer<?>> determineImportersByCommandLine(CommandLine cmd) {
        List<Importer<?>> importers = new ArrayList<>();
        if (cmd.hasOption(OPTION_IMPORT_MY_SKLAD_PRODUCT)) {
            String fileName = cmd.getOptionValue(OPTION_IMPORT_MY_SKLAD_PRODUCT);
            importers.add(msProductImporterFactory.apply(fileName));
        }
        if (cmd.hasOption(OPTION_IMPORT_SAMSON_PRODUCT)) {
            String fileName = cmd.getOptionValue(OPTION_IMPORT_SAMSON_PRODUCT);
            importers.add(samsonProductImporterFactory.apply(fileName));
        }
        if (importers.isEmpty()) {
            throw new IllegalArgumentException("No importer found");
        }
        return importers;
    }
}
