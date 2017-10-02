package hello;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.junit4.SpringRunner;

import com.rometools.rome.feed.synd.SyndEntryImpl;

/**
 * 
 * This uses Spring Boot’s test support to set a property auto.startup to false. 
 * 
 * It is generally not a good idea to rely on a network connection for tests, especially in a CI environment. 
 * So, instead, we prevent the feed adapter from starting and inject a SyndEntry into the news channel for processing by the rest of the flow. 
 * 
 * The test also sets the feed.file.name so the test writes to a different file; then:
 * - verifies the adapter is stopped
 * - creates a test SyndEntry
 * - deletes the test output file (if it’s present)
 * - sends the message
 * - verifies the file exists
 * - reads the file and verifies that the data is as expected
 * 
 * 
 * @author ex532
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest({ "auto.startup=false",      // we don't want to start the real feed
                  "feed.file.name=Test" })   // use a different file
public class FlowTests {

	@Autowired
	private SourcePollingChannelAdapter newsAdapter;

	@Autowired
	private MessageChannel news;

	@Test
	public void test() throws Exception {
		assertThat(this.newsAdapter.isRunning()).isFalse();
		SyndEntryImpl syndEntry = new SyndEntryImpl();
		syndEntry.setTitle("Test Title");
		syndEntry.setLink("http://foo/bar");
		File out = new File("/tmp/si/Test");
		out.delete();
		assertThat(out.exists()).isFalse();
		this.news.send(MessageBuilder.withPayload(syndEntry).build());
		assertThat(out.exists()).isTrue();
		BufferedReader br = new BufferedReader(new FileReader(out));
		String line = br.readLine();
		assertThat(line).isEqualTo("Test Title @ http://foo/bar");
		br.close();
		out.delete();
	}

}
