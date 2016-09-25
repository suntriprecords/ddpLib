# ddpLib
A DDP (Disc Description Protocol) image manipulation lib

DDP masters are used in the CD replication industry: https://en.wikipedia.org/wiki/Disc_Description_Protocol

It was initiated in 2011 as part of the Suntrip Records systems with the goal to extract the label's CD masters perfectly. It was not really tested with DVDs, and very little with other CD specs (CDi, CD-Text, etc)
Further work was added in 2013 to try to build a DDP writer + its UI (something similar to Sonoris' DDP Creator for example) but it was never completed.

## Specs
This lib aims at being compliant with the DCA spec v1.01, 2.0 and 2.10, but doesn not support v2.10 as of today (2016-09-25)
To obtain a license and the specs, just apply to DCA (Doug Carson & Associates, Inc). It's free. http://www.dcainc.com/products/ddplicense/
The CD-Text logic implements a part of the SCSI MMC-3 spec (see http://www.t10.org/drafts.htm) is also inspired by Linux' cdrtools code (but has less bugs)
The lib requires Java 7.

## How-to
- Extract a DDP image: Build a the jar and run with java -jar, or run the class com.suntriprecords.ddp.ui.DdpImageExtractor
- Get DDP image info to display : new DdpInfo(image).getInfo()
- Extract DDP: AbstractDdpImage image = DdpImageFactory.load(inputDir); and new DdpTrackDumper(image).dumpAllTracks(outputDir, true/false);


## TODO
- Psec for trackNumber is 00h and indexNumber is A0h
- Handle Philips CD-Text
- Implement DDP 2.10
- DDP Builder
- generation of md5 check (MD5-Checksum.md5) or CRC32 check (CHECKSUM.TXT) check
    Format:
        <MD5 on 32 hex digits> *<file>
        or for CRC32
        Comments [...]
        Version=1.01
        <file>=<crc32 on 8 hex digits> (= is not mandatory)


## Reminders
Each second of a CD is 75 sectors.
There are 98 frames per sector. 2 are synchronization (not on the master, that is the DDP), 96 are data + subcode.
Each audio frame is made up of 1 byte of subcode, 2*12 bytes of data, 4 bytes of Q parity, 4 bytes of P parity.
That's why you have 24*98=2352 bytes of *usable* data per sector.
1 CD of 800MB ~ 340000 sectors, that is ~33.3M frames, which means we can also store ~32.6MB of subcode.

The subcode in on a separate file on the master.
Each subcode byte is 8 bits PQRSTUVW (or WVUTSRQP depending on the SUB layout). They are arranged in 4 packs of 24 bytes across each sector (4*24=96).
Each pack is 1 byte of mode item, 1 byte of instruction, 2 bytes of Q parity, 16 bytes of data, 4 bytes of P parity.
There seems to be a particular case when SSM=8 (Complete 2352 with R-W) where the interleaved subcode may be 4 times 24 or 18 bytes per sector. The 18-byte variant has no parity.
