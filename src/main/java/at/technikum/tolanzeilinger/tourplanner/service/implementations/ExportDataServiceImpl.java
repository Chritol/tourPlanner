package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.constants.ExcelHeaderConstants;
import at.technikum.tolanzeilinger.tourplanner.constants.PropertyConstants;
import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.helpers.FileNameGenerator;
import at.technikum.tolanzeilinger.tourplanner.helpers.FileType;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourLogDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourLogRepository;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.ExportDataService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.FolderOpenerService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.PropertyLoaderService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportDataServiceImpl implements ExportDataService {
    public static final String[] HEADERS = new String[]{
            ExcelHeaderConstants.TOUR_NAME,
            ExcelHeaderConstants.DESCRIPTION,
            ExcelHeaderConstants.DESTINATION_FROM,
            ExcelHeaderConstants.DESTINATION_TO,
            ExcelHeaderConstants.TRANSPORTATION_TYPE,
            ExcelHeaderConstants.DISTANCE,
            ExcelHeaderConstants.ESTIMATED_TIME,
            ExcelHeaderConstants.HILL_TYPE,
            ExcelHeaderConstants.POPULARITY,
            ExcelHeaderConstants.CHILD_FRIENDLINESS,
            ExcelHeaderConstants.LOG_DATE,
            ExcelHeaderConstants.COMMENT,
            ExcelHeaderConstants.DIFFICULTY,
            ExcelHeaderConstants.TOTAL_TIME,
            ExcelHeaderConstants.RATING
    };
    private final Logger logger;
    private final TourRepository tourRepository;
    private final TourLogRepository tourLogRepository;
    private final PropertyLoaderService propertyLoaderService;
    private final FolderOpenerService folderOpenerService;

    private final EventAggregator eventAggregator;

    public ExportDataServiceImpl(Logger logger,
                                 TourRepository tourRepository,
                                 TourLogRepository tourLogRepository,
                                 PropertyLoaderService propertyLoaderService,
                                 FolderOpenerService folderOpenerService,
                                 EventAggregator eventAggregator) {
        this.logger = logger;
        this.tourRepository = tourRepository;
        this.tourLogRepository = tourLogRepository;
        this.propertyLoaderService = propertyLoaderService;
        this.folderOpenerService = folderOpenerService;
        this.eventAggregator = eventAggregator;
    }

    @Override
    public void exportToursAndLogs() {
        String rootPath = propertyLoaderService.getProperty(PropertyConstants.REPORT_SAVE_PATH);
        folderOpenerService.createDirectoryIfNotExists(rootPath);
        Map<TourDaoModel, List<TourLogDaoModel>> tourListMap = getAllTours();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Tour Data");

            addHeaderToSheet(sheet);
            writeDataToSheet(tourListMap, sheet);

            try (FileOutputStream fos = new FileOutputStream(FileNameGenerator.generateFileName(rootPath, "tourData", FileType.EXCEL))) {
                workbook.write(fos);
            }

            eventAggregator.publish(Event.EXCEL_CREATED);
            logger.info("Exported data successfully");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void addHeaderToSheet(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(HEADERS[i]);
        }
    }

    private static void writeDataToSheet(Map<TourDaoModel, List<TourLogDaoModel>> tourListMap, Sheet sheet) {
        int rowIndex = 1;
        for (Map.Entry<TourDaoModel, List<TourLogDaoModel>> entry : tourListMap.entrySet()) {
            TourDaoModel currentTour = entry.getKey();
            List<TourLogDaoModel> currentTourLogs = entry.getValue();

            if(currentTourLogs.size() == 0) {
                Row dataRow = sheet.createRow(rowIndex++);
                writeTourData(currentTour, dataRow, 0);
            } else {
                for (TourLogDaoModel currentTourLog : currentTourLogs) {
                    Row dataRow = sheet.createRow(rowIndex++);
                    int cellIndex = 0;

                    cellIndex = writeTourData(currentTour, dataRow, cellIndex);
                    writeLogData(currentTourLog, dataRow, cellIndex);
                }
            }
        }
    }

    private static void writeLogData(TourLogDaoModel selectedLog, Row dataRow, int cellIndex) {
        dataRow.createCell(cellIndex++).setCellValue(selectedLog.getLogDateTime().toString());
        dataRow.createCell(cellIndex++).setCellValue(selectedLog.getComment());
        dataRow.createCell(cellIndex++).setCellValue(selectedLog.getDifficulty().name());
        dataRow.createCell(cellIndex++).setCellValue(selectedLog.getTotalTime());
        dataRow.createCell(cellIndex).setCellValue(selectedLog.getRating());
    }

    private static int writeTourData(TourDaoModel currentTour, Row dataRow, int cellIndex) {
        dataRow.createCell(cellIndex++).setCellValue(currentTour.getName());
        dataRow.createCell(cellIndex++).setCellValue(currentTour.getDescription());
        dataRow.createCell(cellIndex++).setCellValue(currentTour.getDestinationFrom());
        dataRow.createCell(cellIndex++).setCellValue(currentTour.getDestinationTo());
        dataRow.createCell(cellIndex++).setCellValue(currentTour.getTransportationType().name());
        dataRow.createCell(cellIndex++).setCellValue(currentTour.getDistance());
        dataRow.createCell(cellIndex++).setCellValue(currentTour.getEstimatedTime());
        dataRow.createCell(cellIndex++).setCellValue(currentTour.getHillType().name());
        dataRow.createCell(cellIndex++).setCellValue(currentTour.getPopularityDaoEnum().name());
        dataRow.createCell(cellIndex++).setCellValue(currentTour.getChildFriendlinessDaoEnum().name());
        return cellIndex;
    }

    private Map<TourDaoModel, List<TourLogDaoModel>> getAllTours() {
        Map<TourDaoModel, List<TourLogDaoModel>> allData = new HashMap<>();
        List<TourDaoModel> allTours = tourRepository.findAll();

        if(allTours != null && allTours.size() > 0) {
            for (TourDaoModel it: allTours) {
                List<TourLogDaoModel> logs = tourLogRepository.findAllOfTour(it);
                allData.put(it, logs != null ? logs : new ArrayList<>());
            }
        }

        return allData;
    }
}
