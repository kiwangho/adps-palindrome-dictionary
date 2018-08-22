package palindrome;

import javax.xml.bind.annotation.*;

//==================================================================
// This corresponds to the element <entry> that comes back
//   in the DictionaryAPI XML response.  Each <entry> is
//   nested under the parent <entry_list>.
//==================================================================
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="entry")
public class DictionaryAPIEntry {
    @XmlElement(name="ew")
    private String ew;
    @XmlElement(name="def")
    private String def;

    public String getEw() {
        return ew;
    }

    public void setEw(String ew) {
        this.ew = ew;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

}
