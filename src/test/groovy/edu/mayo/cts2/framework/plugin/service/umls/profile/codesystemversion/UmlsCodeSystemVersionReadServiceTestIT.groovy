package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystemversion

import edu.mayo.cts2.framework.core.xml.Cts2Marshaller
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.umls.profile.codesystem.UmlsCodeSystemReadService
import edu.mayo.cts2.framework.plugin.service.umls.profile.codesystemversion.UmlsCodeSystemVersionReadService
import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase
import org.junit.Test

import javax.annotation.Resource
import javax.xml.transform.stream.StreamResult

import static org.junit.Assert.*

class UmlsCodeSystemVersionReadServiceTestIT extends AbstractTestITBase {

	@Resource
	UmlsCodeSystemVersionReadService service

	@Resource
	Cts2Marshaller marshaller
	
	def doRead() {
		def cs = service.read(ModelUtils.nameOrUriFromName("LNC240"), null)
	
		cs
	}
	
	@Test
	void testSetUp(){
		assertNotNull service
	}

	@Test
	void testRead()
	{
		def csn = service.read(ModelUtils.nameOrUriFromName("LNC240"), null)
		assertNotNull csn
	}

	@Test
	void testExists()
	{
		assertTrue service.exists(ModelUtils.nameOrUriFromName("LNC240"), null)
		assertFalse service.exists(ModelUtils.nameOrUriFromName("CODESYSTEMNOTTHERE"), null)
	}

	
	@Test
	void TestValidXml() {
		def cs = doRead()

		assertNotNull cs

		marshaller.marshal(cs, new StreamResult(new StringWriter()))
	}
}