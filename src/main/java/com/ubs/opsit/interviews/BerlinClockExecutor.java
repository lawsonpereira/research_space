/**
 * 
 */
package com.ubs.opsit.interviews;

import java.util.ArrayList;
import java.util.List;
import static com.ubs.opsit.interviews.constants.BerlinClockTimeConstants.*;

/**
 * @author Lawson Pereira
 *
 */
public class BerlinClockExecutor implements TimeConverter {

	private volatile List<String> lstTime = null;

	private int iSeconds = 0;
	private int iHours = 0;
	private int iMinute = 0;

	private static final String NEXT_LINE = System.getProperty("line.separator");

	/**
	 * For Testing the time
	 */
	public static void main(String[] args) {
		// String strTime = args[0];
		BerlinClockExecutor berlinClockExecutor = new BerlinClockExecutor();
		berlinClockExecutor.convertTime("24:00:00");
	}
	
	
	/**This method takes value in int datatype of hour and flashes the lamps
	 * @param iHour
	 * @throws 
	 */
	private void calculateHour(int iHour) {
		StringBuffer sbRow = null;
		for (int i = 0; i < FIRST_HOUR_ROW; i++) {
			if (i == 0) {
				sbRow = new StringBuffer();
			}
			if (iHour >= FIRST_HOUR_ROW_BLOCK_VALUE) {
				sbRow.append("R");
				iHour = iHour - FIRST_HOUR_ROW_BLOCK_VALUE;
			} else {
				sbRow.append("0");
			}
		}
		lstTime.add(sbRow.toString());
		for (int i = 0; i < SECOND_HOUR_ROW; i++) {
			if (i == 0) {
				sbRow = new StringBuffer();
			}
			if (iHour >= SECOND_HOUR_ROW_BLOCK_VALUE) {
				sbRow.append("R");
				iHour = iHour - SECOND_HOUR_ROW_BLOCK_VALUE;
			} else {
				sbRow.append("0");
			}
		}
		lstTime.add(sbRow.toString());

	}
	/**This method takes value in int datatype of minute and flashes the lamps
	 * @param iMiniute
	 * @throws 
	 */
	private void calculateMin(int iMiniute) {
		StringBuffer sbRow = null;
		for (int i = 0; i < FIRST_MIN_ROW; i++) {
			if (i == 0) {
				sbRow = new StringBuffer();
			}
			if (iMiniute >= FIRST_MIN_ROW_BLOCK_VALUE) {
				if ((i + 1) % TIME_QUARTER == 0) {
					sbRow.append("R");
				} else {
					sbRow.append("Y");
				}
				iMiniute = iMiniute - FIRST_MIN_ROW_BLOCK_VALUE;
			} else {
				sbRow.append("0");
			}
		}
		lstTime.add(sbRow.toString());
		for (int i = 0; i < SECOND_MIN_ROW; i++) {
			if (i == 0) {
				sbRow = new StringBuffer();
			}
			if (iMiniute >= SECOND_MIN_ROW_BLOCK_VALUE) {
				sbRow.append("Y");
				iMiniute = iMiniute - SECOND_MIN_ROW_BLOCK_VALUE;
			} else {
				sbRow.append("0");
			}
		}
		lstTime.add(sbRow.toString());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ubs.opsit.interviews.TimeConverter#convertTime(java.lang.String)
	 */
	@Override
	public String convertTime(String aTime) {
		lstTime = new ArrayList<String>();
		try {
			validateTime(aTime);
			calculateSeconds(iSeconds);
			calculateHour(iHours);
			calculateMin(iMinute);
			String strBerlinTime = String.join(NEXT_LINE, lstTime);
			System.out.println(strBerlinTime);
			return strBerlinTime;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**This method validates the time input and throws the necessary exceptions 
	 * if the time is not in proper format. The time given should be in format 0:0:0 to 23:59:59
	 * @param strTime
	 * @throws Exception
	 */
	private void validateTime(String strTime) throws Exception {
		if (strTime == null || strTime.trim().equals("")) {
			throw new Exception("Invalid/Empty time value specified");
		} else {
			String strSplitTime[] = strTime.split(":");
			if (strSplitTime.length != 3) {
				throw new Exception("Invalid time value specified. Please specify time in hh:mm:ss");
			}

			try {
				iHours = Integer.parseInt(strSplitTime[0]);
				iMinute = Integer.parseInt(strSplitTime[1]);
				iSeconds = Integer.parseInt(strSplitTime[2]);
			} catch (Exception e) {
				throw new Exception("Invalid time value specified.");
			}

			if (iHours < 0 || iHours > 24) {
				throw new Exception("Invalid time value specified. Hours should be in range of 0 to 24");
			}
			
			if (iMinute < 0 || iMinute > 59) {
				throw new Exception("Invalid time value specified. Minute should be in range of 0 to 59");
			}
			
			if (iSeconds < 0 || iSeconds > 59) {
				throw new Exception("Invalid time value specified. Seconds should be in range of 0 to 59");
			}
			
			if(iHours == 24 && iMinute != 0 && iSeconds != 0) {
				throw new Exception("Invalid time value specified.");
			}

		}

	}

	/**This method takes value in int datatype of seconds and flashes the lamp after every 2 seconds
	 * @param iSeconds
	 * @throws 
	 */
	private void calculateSeconds(int iSeconds) {
		lstTime.add((iSeconds % 2 == 0) ? "Y" : "0");

	}

}
