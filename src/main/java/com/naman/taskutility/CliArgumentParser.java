package com.naman.taskutility;

public class CliArgumentParser {

    public CliOptions parse(String[] args) {

        if (args.length == 2
                && !args[0].startsWith("--")
                && !args[1].startsWith("--")) {

            CliOptions options = new CliOptions();

            options.setInputFile(args[0]);
            options.setOutputFile(args[1]);

            return options;
        }

        CliOptions options = new CliOptions();

        for (int i = 0; i < args.length; i++) {

            switch (args[i]) {

                case "--help":
                    options.setHelp(true);
                    break;

                case "--input":

                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException(
                                "Missing value for --input");
                    }

                    options.setInputFile(args[++i]);
                    break;

                case "--output":

                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException(
                                "Missing value for --output");
                    }

                    options.setOutputFile(args[++i]);
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Unknown option: " + args[i]);
            }
        }

        return options;
    }
}