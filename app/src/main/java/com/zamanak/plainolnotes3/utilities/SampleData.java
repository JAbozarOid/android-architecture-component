package com.zamanak.plainolnotes3.utilities;

import com.zamanak.plainolnotes3.database.NoteEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * this class is for testing
 */
public class SampleData {

    // we want three notes and each of them demonstrate something different
    // one note can be single line of text
    // one note could be line feed
    // one note could be long text

    private static final String SAMPLE_TEXT_1 = "A simple note";
    private static final String SAMPLE_TEXT_2 = "A simple note with a\nline feed";
    private static final String SAMPLE_TEXT_3 = "Lorem ipsum dolor sit amet, in vel laoreet menandri incorrupte, nec te oportere periculis definiebas. Mei ut munere doctus singulis, possit scripta eu usu, vix nihil doming cu. In cum tota persecuti definiebas, his justo everti ad. Quo te idque torquatos, pri in eirmod volutpat. Reque viderer utroque nam cu, feugait fuisset deseruisse cum at. Minim exerci oporteat at quo.\n" +
            "\n" +
            "Has ea odio causae, et sit prima graeci facilis, nostro audiam definitiones eum id. Ridens bonorum eum ut, ex mei elitr denique apeirian. Tale debet erant sit te, eum ea diam adhuc convenire. Nam ei nibh iudico, at sed scaevola hendrerit. Ex usu dicant dolore. Sed ea veritus appareat, ne ullum quodsi luptatum vis, probatus deserunt qui ut.\n" +
            "\n" +
            "Ex has vitae habemus appareat. Et reque equidem cum, sit ei veri vocent. Sea eu lorem fierent interpretaris, modus lucilius mel ut, eum habeo suscipit percipitur et. Augue primis deterruisset ei nam, in aperiam omittam sea, cu quo porro populo adipisci. His ei alii porro sensibus, ut vis movet abhorreant accommodare. In eum dicam consulatu dissentiunt, ut mea modo latine. Te erat propriae quo, vix duis harum omittam eu, latine euripidis evertitur ad sed.\n" +
            "\n" +
            "No oratio civibus qui, et vel magna omnes voluptaria. Eum possim insolens ut, verear noluisse perfecto pri ad. Sea cu nonumes vocibus, at constituto reprimique mediocritatem pri, cu nec affert percipitur. Ea ius tation maiestatis assueverit, eu causae euismod legendos mel, soleat copiosae perpetua sed ei. Pri id affert volutpat. Quidam mediocrem salutandi et ius, ad nam verear audire omittam, pro id nostrud omittantur.\n" +
            "\n" +
            "Nam et virtute deserunt conceptam, tempor corpora vis ad, solum postea eligendi an has. Vocent meliore vulputate an duo, ex eam doming diceret definitiones. Ne aperiam epicuri nam. Veritus efficiantur ne qui, ut assum munere ius. Invidunt persecuti mel no, has ne quodsi omnium, ferri quaestio suavitate qui an.";

    private static Date getData(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }

    public static List<NoteEntity> getNotes(){
        List<NoteEntity> notes = new ArrayList<>();
        notes.add(new NoteEntity(1,getData(0),SAMPLE_TEXT_1));
        notes.add(new NoteEntity(2,getData(-1),SAMPLE_TEXT_2));
        notes.add(new NoteEntity(3,getData(-2),SAMPLE_TEXT_3));
        return notes;
    }
}
