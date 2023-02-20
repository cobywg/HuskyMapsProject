package autocomplete;
import java.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.*;
/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {  {
        this.terms.addAll(terms);
        Collections.sort(this.terms, CharSequence::compare);
    }
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> result = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) {
            return result;
        }
        int i = Collections.binarySearch(terms, prefix, CharSequence::compare);
        if(i >=0) {
            for(int j = i; j <= terms.size()-i; j++) {
                if(Autocomplete.isPrefixOf(prefix, terms.get(j))) {
                    result.add(terms.get(j));
                }

                }
            }
        Collections.sort(result, CharSequence::compare);
        return result;
        }
    }

