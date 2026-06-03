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

                case "--status":

                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException(
                                "Missing value for --status");
                    }

                    String status = args[++i];

                    if (!status.equalsIgnoreCase("completed")
                            && !status.equalsIgnoreCase("pending")
                            && !status.equalsIgnoreCase("all")) {

                        throw new IllegalArgumentException(
                                "Invalid value for --status: " + status);
                    }

                    options.setStatus(status);
                    break;

                case "--category":

                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException(
                                "Missing value for --category");
                    }

                    options.setCategory(args[++i]);
                    break;

                case "--difficulty":

                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException(
                                "Missing value for --difficulty");
                    }

                    String difficulty = args[++i];

                    if (!difficulty.equalsIgnoreCase("easy")
                            && !difficulty.equalsIgnoreCase("medium")
                            && !difficulty.equalsIgnoreCase("hard")) {

                        throw new IllegalArgumentException(
                                "Invalid value for --difficulty: " + difficulty);
                    }

                    options.setDifficulty(difficulty);
                    break;

                case "--sort-by":

                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException(
                                "Missing value for --sort-by");
                    }

                    String sortBy = args[++i];

                    if (!sortBy.equalsIgnoreCase("title")
                            && !sortBy.equalsIgnoreCase("time")
                            && !sortBy.equalsIgnoreCase("difficulty")) {

                        throw new IllegalArgumentException(
                                "Invalid value for --sort-by: " + sortBy);
                    }

                    options.setSortBy(sortBy);
                    break;

                case "--sort-order":

                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException(
                                "Missing value for --sort-order");
                    }

                    String sortOrder = args[++i];

                    if (!sortOrder.equalsIgnoreCase("asc")
                            && !sortOrder.equalsIgnoreCase("desc")) {

                        throw new IllegalArgumentException(
                                "Invalid value for --sort-order: " + sortOrder);
                    }

                    options.setSortOrder(sortOrder);
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Unknown option: " + args[i]);
            }
        }

        if (options.getSortOrder() != null
                && !options.getSortOrder().isBlank()
                && (options.getSortBy() == null
                || options.getSortBy().isBlank())) {
            throw new IllegalArgumentException(
                    "Missing value for --sort-by");
        }

        return options;
    }
}