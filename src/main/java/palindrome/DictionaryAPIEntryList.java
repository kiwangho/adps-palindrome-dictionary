package palindrome;

import javax.xml.bind.annotation.*;
import java.util.List;


//==================================================================
// This corresponds to the root element <entry_list> that comes back
//   from the DictionaryAPI response.
//==================================================================
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="entry_list")
public class DictionaryAPIEntryList {

    private String version;

    @XmlElement(name="entry", type = DictionaryAPIEntry.class)
    private List<DictionaryAPIEntry> entries;

    public List<DictionaryAPIEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<DictionaryAPIEntry> entries) {
        this.entries = entries;
    }

    public String getVersion() {
        return version;
    }

    @XmlAttribute(name = "version")
    public void setId(String version) {
        this.version = version;
    }

}
