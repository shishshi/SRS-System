package dao;

import java.util.List;

import model.TranscriptEntry;

public interface TranscriptEntryDao {
	List<TranscriptEntry> getAllTranscriptEntrys();
	TranscriptEntry getTranscriptEntry(int transcriptEntryNo);
	void addTranscriptEntry(TranscriptEntry transcriptEntry);
	void deleteTranscriptEntry(TranscriptEntry transcriptEntry);
	void updateTranscriptEntry(TranscriptEntry transcriptEntry);
}
