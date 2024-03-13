
interface AssetScrap {
    scrapId: number;
    assetId: number;
    scrapDate: string; // 或 Date 类型，取决于您如何处理日期
    reason: string | null;
    fixedAsset: FixedAsset;
}


interface AssetRepair {
    repairId: number;
    assetId: number;
    repairDate: string; // 或 Date 类型，取决于您如何处理日期
    cost: string | null; // 或 string 类型，如果您希望保持原始格式
    status: string;
    details: string | null;
    fixedAsset: FixedAsset;
}

interface AssetAllocation {
    allocationId: number | null;
    allocationDescription: string | null;
    status: string | null;
    reason: string | null;
    user: User;
    fixedAsset: FixedAsset;
    allocationDate: string | null;
    returnDate: string | null;
}


interface AssetType {
    assetTypeId: number;
    typeName: string;
    description: string | null;
}

interface FixedAsset {
    assetId: number;
    assetTypeId: number;
    name: string;
    purchaseDate: Date; // 或 Date 类型，取决于您如何处理日期
    price: number; // 或 string 类型，如果您希望保持原始格式
    imgDir: string;
    status: string;
    usefulYear: number;
    assetType: AssetType;
}

interface AssetInventory {
    inventoryId: number;
    inventoryDate: string; // 或 Date 类型，取决于您如何处理日期
    departmentId: number;
    department: Department;
    bookValue: number; // 或 string 类型，如果您希望保持原始格式
    bookQuantity: number;
    inventoryName: string;
}


interface Department {
    departmentId: number | null;
    name: string | null;
    description: string | null;
}

interface Employee {
    employeeId: number | null;
    name: string | null;
    departmentId: number | null;
    department: Department;
}

interface User {
    userId: number | null;
    username: string | null;
    roleId: number | null;
    employeeId: number | null;
    employee: Employee;
}


export type { AssetType, FixedAsset, AssetScrap, AssetRepair, Department, Employee, User, AssetAllocation, AssetInventory};