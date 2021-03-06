package me.devsaki.hentoid.parsers;

import me.devsaki.hentoid.database.domains.Content;
import me.devsaki.hentoid.enums.Site;
import me.devsaki.hentoid.parsers.content.ASMHentaiContent;
import me.devsaki.hentoid.parsers.content.DoujinsContent;
import me.devsaki.hentoid.parsers.content.DummyContent;
import me.devsaki.hentoid.parsers.content.FakkuContent;
import me.devsaki.hentoid.parsers.content.HbrowseContent;
import me.devsaki.hentoid.parsers.content.Hentai2ReadContent;
import me.devsaki.hentoid.parsers.content.HentaiCafeContent;
import me.devsaki.hentoid.parsers.content.HentaifoxContent;
import me.devsaki.hentoid.parsers.content.HitomiContent;
import me.devsaki.hentoid.parsers.content.MusesContent;
import me.devsaki.hentoid.parsers.content.NexusContent;
import me.devsaki.hentoid.parsers.content.NhentaiContent;
import me.devsaki.hentoid.parsers.content.PorncomixContent;
import me.devsaki.hentoid.parsers.content.PururinContent;
import me.devsaki.hentoid.parsers.content.TsuminoContent;
import me.devsaki.hentoid.parsers.images.ASMHentaiParser;
import me.devsaki.hentoid.parsers.images.DoujinsParser;
import me.devsaki.hentoid.parsers.images.DummyParser;
import me.devsaki.hentoid.parsers.images.EHentaiParser;
import me.devsaki.hentoid.parsers.images.ExHentaiParser;
import me.devsaki.hentoid.parsers.images.FakkuParser;
import me.devsaki.hentoid.parsers.images.HbrowseParser;
import me.devsaki.hentoid.parsers.images.Hentai2ReadParser;
import me.devsaki.hentoid.parsers.images.HentaiCafeParser;
import me.devsaki.hentoid.parsers.images.HentaifoxParser;
import me.devsaki.hentoid.parsers.images.HitomiParser;
import me.devsaki.hentoid.parsers.images.ImageListParser;
import me.devsaki.hentoid.parsers.images.LusciousParser;
import me.devsaki.hentoid.parsers.images.MusesParser;
import me.devsaki.hentoid.parsers.images.NexusParser;
import me.devsaki.hentoid.parsers.images.NhentaiParser;
import me.devsaki.hentoid.parsers.images.PorncomixParser;
import me.devsaki.hentoid.parsers.images.PururinParser;
import me.devsaki.hentoid.parsers.images.TsuminoParser;

public class ContentParserFactory {

    private static final ContentParserFactory mInstance = new ContentParserFactory();

    private ContentParserFactory() {
    }

    public static ContentParserFactory getInstance() {
        return mInstance;
    }


    public Class getContentParserClass(Site site) {
        switch (site) {
            case NHENTAI:
                return NhentaiContent.class;
            case ASMHENTAI:
            case ASMHENTAI_COMICS:
                return ASMHentaiContent.class;
            case HENTAICAFE:
                return HentaiCafeContent.class;
            case HITOMI:
                return HitomiContent.class;
            case TSUMINO:
                return TsuminoContent.class;
            case PURURIN:
                return PururinContent.class;
            case FAKKU2:
                return FakkuContent.class;
            case NEXUS:
                return NexusContent.class;
            case MUSES:
                return MusesContent.class;
            case DOUJINS:
                return DoujinsContent.class;
            case PORNCOMIX:
                return PorncomixContent.class;
            case HBROWSE:
                return HbrowseContent.class;
            case HENTAI2READ:
                return Hentai2ReadContent.class;
            case HENTAIFOX:
                return HentaifoxContent.class;
            case EHENTAI: // Uses the API of the site -> no HTML parser
            case EXHENTAI: // Uses the API of the site -> no HTML parser
            case LUSCIOUS: // Uses the API of the site -> no HTML parser
            default:
                return DummyContent.class;
        }
    }

    public ImageListParser getImageListParser(Content content) {
        return (null == content) ? new DummyParser() : getImageListParser(content.getSite());
    }

    public ImageListParser getImageListParser(Site site) {
        switch (site) {
            case ASMHENTAI:
            case ASMHENTAI_COMICS:
                return new ASMHentaiParser();
            case HENTAICAFE:
                return new HentaiCafeParser();
            case HITOMI:
                return new HitomiParser();
            case TSUMINO:
                return new TsuminoParser();
            case PURURIN:
                return new PururinParser();
            case EHENTAI:
                return new EHentaiParser();
            case EXHENTAI:
                return new ExHentaiParser();
            case FAKKU2:
                return new FakkuParser();
            case NEXUS:
                return new NexusParser();
            case LUSCIOUS:
                return new LusciousParser();
            case PORNCOMIX:
                return new PorncomixParser();
            case MUSES:
                return new MusesParser();
            case NHENTAI:
                return new NhentaiParser();
            case DOUJINS:
                return new DoujinsParser();
            case HBROWSE:
                return new HbrowseParser();
            case HENTAI2READ:
                return new Hentai2ReadParser();
            case HENTAIFOX:
                return new HentaifoxParser();
            default:
                return new DummyParser();
        }
    }
}
