package task;

import java.util.stream.Collectors;

public class RecordFormatter {

    public String format(Record record) {
        return record.formattedRecord(record);
    }
}
