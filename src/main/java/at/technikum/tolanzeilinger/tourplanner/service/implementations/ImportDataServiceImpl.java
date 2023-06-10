package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.constants.ExcelHeaderConstants;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.model.TourLog;
import at.technikum.tolanzeilinger.tourplanner.model.enums.Difficulty;
import at.technikum.tolanzeilinger.tourplanner.model.enums.HillType;
import at.technikum.tolanzeilinger.tourplanner.model.enums.Transportation;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.*;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.ImportDataService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourLogService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class ImportDataServiceImpl implements ImportDataService {
    private final Logger logger;
    private final TourService tourService;
    private final TourLogService tourLogService;

    public ImportDataServiceImpl(Logger logger,
                                 TourService tourService,
                                 TourLogService tourLogService) {
        this.logger = logger;
        this.tourService = tourService;
        this.tourLogService = tourLogService;
    }

    @Override
    public void importExcel(String importPath) {
        try (Workbook workbook = WorkbookFactory.create(new File(importPath))) {
            Sheet sheet = workbook.getSheetAt(0);

            Tour currentTour = null;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Skip the header row
                    continue;
                }

                int cellIndex = 0;
                Iterator<Cell> cellIterator = row.cellIterator();

                // Tour fields
                String tourName = "n/a";
                String description = "n/a";
                String destinationFrom = "n/a";
                String destinationTo = "n/a";
                Transportation transportation = Transportation.BIKE;
                HillType hillType = HillType.DEFAULT_STRATEGY;

                // Log fields
                LocalDateTime logDate = LocalDateTime.MIN;
                String comment = "n/a";
                Difficulty difficulty = Difficulty.EASY;
                Integer totalTime = 0;
                Integer rating = 0;

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String header = sheet.getRow(0).getCell(cellIndex).getStringCellValue();

                    switch (header) {
                        case ExcelHeaderConstants.TOUR_NAME:
                            tourName = Optional.ofNullable(cell.getStringCellValue()).orElse(tourName);
                            break;
                        case ExcelHeaderConstants.DESCRIPTION:
                            description = Optional.ofNullable(cell.getStringCellValue()).orElse(description);
                            break;
                        case ExcelHeaderConstants.DESTINATION_FROM:
                            destinationFrom = Optional.ofNullable(cell.getStringCellValue()).orElse(destinationFrom);
                            break;
                        case ExcelHeaderConstants.DESTINATION_TO:
                            destinationTo = Optional.ofNullable(cell.getStringCellValue()).orElse(destinationTo);
                            break;
                        case ExcelHeaderConstants.TRANSPORTATION_TYPE:
                            transportation = Transportation.valueOf(Optional.ofNullable(cell.getStringCellValue()).orElse(transportation.name()));
                            break;
                        case ExcelHeaderConstants.HILL_TYPE:
                            hillType = HillType.valueOf(Optional.ofNullable(cell.getStringCellValue()).orElse(hillType.name()));
                            break;
                        case ExcelHeaderConstants.LOG_DATE:
                            logDate = LocalDateTime.parse(Optional.ofNullable(cell.getStringCellValue()).orElse(logDate.toString()));
                            break;
                        case ExcelHeaderConstants.COMMENT:
                            comment = Optional.ofNullable(cell.getStringCellValue()).orElse(comment);
                            break;
                        case ExcelHeaderConstants.DIFFICULTY:
                            difficulty = Difficulty.valueOf(Optional.ofNullable(cell.getStringCellValue()).orElse(difficulty.name()));
                            break;
                        case ExcelHeaderConstants.TOTAL_TIME:
                            totalTime = (Optional.ofNullable(cell.getNumericCellValue()).orElse(totalTime.doubleValue())).intValue();
                            break;
                        case ExcelHeaderConstants.RATING:
                            rating = (Optional.ofNullable(cell.getNumericCellValue()).orElse(rating.doubleValue())).intValue();
                            break;
                    }

                    cellIndex++;
                }

                if(currentTour == null || !(currentTour.getName().equals(tourName) &&
                                currentTour.getDescription().equals(description) &&
                                currentTour.getFrom().equals(destinationFrom) &&
                                currentTour.getTo().equals(destinationTo) &&
                                currentTour.getHilliness().equals(hillType) &&
                                currentTour.getTransportation().equals(transportation))) {
                    Tour tour = new Tour();
                    tour.setName(tourName);
                    tour.setDescription(description);
                    tour.setFrom(destinationFrom);
                    tour.setTo(destinationTo);
                    tour.setHilliness(hillType);
                    tour.setTransportation(transportation);

                    tourService.addTour(tour);
                    currentTour = tourService.getActiveTour();
                }

                TourLog tourLog = new TourLog();
                tourLog.setTour(currentTour);
                tourLog.setComment(comment);
                tourLog.setDifficulty(difficulty);
                tourLog.setRating(rating);
                tourLog.setTotalTime(totalTime);
                tourLog.setLogDateTime(logDate);

                tourLogService.addLog(tourLog);
            }
            logger.info("Imported data successfully");
        } catch (IOException e) {
            logger.error("Failed to import data: " + e.getMessage(), e);
        }
    }
}
